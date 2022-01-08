using Microsoft.AspNetCore.Mvc;
using Music.WebApi.Models.Music;
using static Music.WebApi.Service;
using static Music.WebApi.Data.AudioData;
using static Music.WebApi.Data.MusicData;

namespace Music.WebApi.Controllers;

[ApiController]
[Route("apis/[controller]")]
public class MusicController : ControllerBase
{
    [HttpGet("/music/songs/get")]
    public async Task<IList<Song>> GetSongs()
    {
        if (SheetsService is null) throw new NullReferenceException(nameof(SheetsService));
        var songs = new List<Song>();
        var songRange = await SheetsService.Spreadsheets.Values
            .Get(SpreadsheetId, AudioRange).ExecuteAsync();
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
        if (SheetsService is null) throw new NullReferenceException(nameof(SheetsService));
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