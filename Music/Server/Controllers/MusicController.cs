using Microsoft.AspNetCore.Mvc;
using Music.Shared.Models;
using static Music.Server.Service;
using static Music.Shared.Cores.Spreadsheet;

namespace Music.Server.Controllers;

[ApiController()]
[Route("apis/[controller]")]
public class MusicController : ControllerBase
{
    private readonly ILogger<MusicController> logger;
    private readonly string id = JsonValues.Id["music"];

    public MusicController(ILogger<MusicController> logger)
    {
        this.logger = logger;
    }

    [HttpGet("songs/get")]
    public async Task<IList<Song>> GetSongs()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(id, JsonValues.Range["audio"]).ExecuteAsync();
        return Serializer.Deserialize<IList<Song>>(responseBody.Values);
    }

    [HttpGet("artists/get")]
    public async Task<IList<Artist>> GetArtists()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(id, JsonValues.Range["artist"]).ExecuteAsync();
        return Serializer.Deserialize<IList<Artist>>(responseBody.Values);
    }

    [HttpGet("shorts/get")]
    public async Task<IList<Short>> GetShorts()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(id, JsonValues.Range["short"]).ExecuteAsync();
        return Serializer.Deserialize<IList<Short>>(responseBody.Values);
    }
}