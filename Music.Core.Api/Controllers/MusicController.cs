using Microsoft.AspNetCore.Mvc;
using Music.Core;
using Music.Core.Models.Music;
using Music.Core.Models.Spreadsheet;

namespace Music.WebApi.Controllers;

[ApiController]
[Route("apis/[controller]")]
public class MusicController : ControllerBase
{
    private readonly SpreadsheetJson spreadsheet = Service.SpreadsheetJson;

    [HttpGet("/music/songs/get")]
    public async Task<IList<Song>> GetSongs()
    {
        var responseBody = await Service.SheetsService.Spreadsheets.Values
            .Get(spreadsheet.Id["music"], spreadsheet.Range["audio"]).ExecuteAsync();
        return responseBody.Values.ToSongs();
    }

    [HttpGet("/music/artists/get")]
    public async Task<IList<Artist>> GetArtists()
    {
        var responseBody = await Service.SheetsService.Spreadsheets.Values
            .Get(spreadsheet.Id["music"], spreadsheet.Range["artist"]).ExecuteAsync();
        return responseBody.Values.ToArtists();
    }
}