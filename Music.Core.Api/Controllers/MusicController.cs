using Microsoft.AspNetCore.Mvc;
using Music.Core.Models;
using static Music.Core.Services.ServerService;

namespace Music.WebApi.Controllers;

[ApiController]
[Route("apis/[controller]")]
public class MusicController : ControllerBase
{
    [HttpGet("/music/songs/get")]
    public async Task<IList<Song>> GetSongs()
    {
        if (Instance.SheetsService is null) throw new NullReferenceException(nameof(Instance.SheetsService));
        var songs = new List<Song>();
        var songRange = await Instance.SheetsService.Spreadsheets.Values
            .Get(Instance.SpreadsheetElement.GetString("id", "music"), AudioRange).ExecuteAsync();
        foreach (var row in songRange.Values)
        {
            var song = new Song()
            {
                Id = row[Id].GetString(),
                ArtistId = row[ArtistId].GetString(),
                VietnameseName = row[VietnameseName].GetString(),
                SimplifiedChineseName = row[SimplifiedChineseName].GetString(),
                TraditionalChineseName = row[TraditionalChineseName].GetString(),
                PinyinName = row[PinyinName].GetString(),
                Duration = row[Duration].GetInteger()
            };
            songs.Add(song);
        }
        return songs;
    }

    [HttpGet("/music/artists/get")]
    public async Task<IList<Artist>> GetArtists()
    {
        if (Instance.SheetsService is null) throw new NullReferenceException(nameof(Instance.SheetsService));
        var artists = new List<Artist>();
        var artistRange = await SheetsService.Spreadsheets.Values
            .Get(SpreadsheetId, ArtistRange).ExecuteAsync();
        foreach (var row in artistRange.Values)
        {
            var artist = new Artist()
            {
                Id = row[Id].GetString(),
                PinyinName = row[PinyinName].GetString(),
                VietnameseName = row[VietnameseName].GetString(),
                SimplifiedChineseName = row[SimplifiedChineseName].GetString(),
                TraditionalChineseName = row[TraditionalChineseName].GetString()
            };
            artists.Add(artist);
        }
        return artists;
    }
}