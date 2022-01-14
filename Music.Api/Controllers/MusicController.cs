using Microsoft.AspNetCore.Mvc;
using Music.Models;
using static Music.Api.Service;
using static Music.Cores.Spreadsheet;

namespace Music.Api.Controllers;

[ApiController]
[Route("[controller]/apis")]
public class MusicController : ControllerBase
{
    private readonly string id = JsonValues.Id["music"];

    [HttpGet("/music/songs/get")]
    public async Task<IList<Song>> GetSongs()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(id, JsonValues.Range["audio"]).ExecuteAsync();
        return Serializer.Deserialize<IList<Song>>(responseBody.Values);
    }

    [HttpGet("/music/artists/get")]
    public async Task<IList<Artist>> GetArtists()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(id, JsonValues.Range["artist"]).ExecuteAsync();
        return Serializer.Deserialize<IList<Artist>>(responseBody.Values);
    }
}