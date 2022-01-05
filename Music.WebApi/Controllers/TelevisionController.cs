using Google.Apis.Sheets.v4.Data;
using Microsoft.AspNetCore.Mvc;
using Music.WebApi.Models;
using static Music.WebApi.Controllers.MusicController;
using static Google.Apis.Sheets.v4.SpreadsheetsResource.ValuesResource.UpdateRequest;

namespace Music.WebApi.Controllers;

public class TelevisionController : ControllerBase
{
    private const string SPREADSHEET_ID = "15XE1J5YPKyCoEHHJabM7IDQeK_wgSnKbWVRvn9eqVVc";
    private const string PLAYLIST_RANGE = "Playlist!A1:G";
    private const string INFORMATION_RANGE = "Information";

    [HttpGet("/television/informations")]
    public async Task<IList<string>> GetInformations()
    {
        var informations = new List<string>();
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(SPREADSHEET_ID, INFORMATION_RANGE).ExecuteAsync();
        foreach (var row in responseBody.Values)
        {
            informations.Add(row[PrimaryId].GetString());
        }
        return informations;
    }

    [HttpGet("/television/playlist/songs/get")]
    public async Task<IList<Song>> GetPlaylist()
    {
        var songs = new List<Song>();
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(SPREADSHEET_ID, PLAYLIST_RANGE).ExecuteAsync();
        foreach (var row in responseBody.Values)
        {
            var song = new Song()
            {
                Id = row[PrimaryId].GetString(),
                ArtistId = row[ReferenceId].GetString(),
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

    [HttpPost("/television/playlist/songs/update")]
    public async Task<string> UpdatePlaylist(IList<string> songs)
    {
        var rows = new List<IList<object?>>();
        foreach (var song in songs)
        {
            var row = new List<object?>
            {
                song
            };
            rows.Add(row);
        }
        var requestBody = new ValueRange() { Values = rows };
        var request = SheetsService.Spreadsheets.Values.Update(requestBody, SPREADSHEET_ID, PLAYLIST_RANGE);
        request.ValueInputOption = ValueInputOptionEnum.RAW;
        var response = await request.ExecuteAsync();
        return response.UpdatedRange;
    }
}