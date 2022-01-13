using Microsoft.AspNetCore.Mvc;
using Music.Core.Models;
using static Music.Core.Api.Service;
using static Music.Core.Spreadsheet;

namespace Music.Core.Api.Controllers;

[ApiController]
[Route("apis/[controller]")]
public class MusicController : ControllerBase
{
    private readonly string id = JsonValues.Id["music"];

    [HttpGet("/music/songs/get")]
    public async Task<IList<Song>> GetSongs()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(id, JsonValues.Range["audio"]).ExecuteAsync();
        return Spreadsheet.Convert.ToSongs(responseBody.Values);
    }

    [HttpGet("/music/artists/get")]
    public async Task<IList<Artist>> GetArtists()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(id, JsonValues.Range["artist"]).ExecuteAsync();
        return Spreadsheet.Convert.ToArtists(responseBody.Values);
    }
}