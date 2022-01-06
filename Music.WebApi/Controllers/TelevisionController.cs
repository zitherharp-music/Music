using Google.Apis.Sheets.v4.Data;
using Microsoft.AspNetCore.Mvc;
using Music.WebApi.Models;
using Music.WebApi.Models.Television;
using static Music.WebApi.Controllers.MusicController;
using AppendValueInputOption = Google.Apis.Sheets.v4.SpreadsheetsResource.ValuesResource.AppendRequest.ValueInputOptionEnum;
using UpdateValueInputOption = Google.Apis.Sheets.v4.SpreadsheetsResource.ValuesResource.UpdateRequest.ValueInputOptionEnum;

namespace Music.WebApi.Controllers;

public class TelevisionController : ControllerBase
{
    private const string SPREADSHEET_ID = "15XE1J5YPKyCoEHHJabM7IDQeK_wgSnKbWVRvn9eqVVc";
    private const string PLAYLIST_RANGE = "Playlist!A2:G";
    private const string INFORMATION_RANGE = "Information";
    private const string MESSAGE_RANGE = "Message!A1:D";
    private const string PLAYINGSONG_RANGE = "Playlist!A1:G";

    [HttpGet("/television/informations/get")]
    public async Task<IList<string>> GetInformations()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(SPREADSHEET_ID, INFORMATION_RANGE).ExecuteAsync();
        var informations = new List<string>();
        foreach (var row in responseBody.Values)
        {
            informations.Add(row[PrimaryId].GetString());
        }
        return informations;
    }

    [HttpGet("/television/playlist/get")]
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

    [HttpPost("/television/playlist/append")]
    public async Task<string> AppendPlaylist([FromBody] IList<Song> songs)
    {
        var rows = new List<IList<object?>>();
        foreach (var song in songs)
        {
            var row = new List<object?>
            {
                song.Id,
                song.ArtistId,
                song.VietnameseName,
                song.SimplifiedChineseName,
                song.TraditionalChineseName,
                song.PinyinName,
                song.Duration
            };
            rows.Add(row);
        }
        var requestBody = new ValueRange() { Values = rows };
        var request = SheetsService.Spreadsheets.Values.Append(requestBody, SPREADSHEET_ID, PLAYLIST_RANGE);
        request.ValueInputOption = AppendValueInputOption.RAW;
        var response = await request.ExecuteAsync();
        return response.TableRange;
    }

    [HttpPut("/television/playlist/update")]
    public async Task<string> UpdatePlaylist([FromBody] IList<Song> songs)
    {
        var rows = new List<IList<object?>>();
        foreach (var song in songs)
        {
            var row = new List<object?>
            {
                song.Id,
                song.ArtistId,
                song.VietnameseName,
                song.SimplifiedChineseName,
                song.TraditionalChineseName,
                song.PinyinName,
                song.Duration
            };
            rows.Add(row);
        }
        var requestBody = new ValueRange() { Values = rows };
        var request = SheetsService.Spreadsheets.Values.Update(requestBody, SPREADSHEET_ID, PLAYLIST_RANGE);
        request.ValueInputOption = UpdateValueInputOption.RAW;
        var response = await request.ExecuteAsync();
        return response.UpdatedRange;
    }

    [HttpGet("/television/playingsong/get")]
    public async Task<Song> GetPlayingSong()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(SPREADSHEET_ID, PLAYINGSONG_RANGE).ExecuteAsync();
        foreach (var row in responseBody.Values)
        {
            return new Song()
            {
                Id = row[PrimaryId].GetString(),
                ArtistId = row[ReferenceId].GetString(),
                VietnameseName = row[VietnameseName].GetString(),
                SimplifiedChineseName = row[SimplifiedChineseName].GetString(),
                TraditionalChineseName = row[TraditionalChineseName].GetString(),
                PinyinName = row[PinyinName].GetString(),
                Duration = row[Duration].GetInteger()
            };
        }
        throw new NullReferenceException(nameof(responseBody));
    }

    [HttpPost("/television/playingsong/append")]
    public async Task<string> AppendPlayingSong([FromBody] Song song)
    {
        var row = new List<IList<object?>>()
        {
            new List<object?>()
            {
                song.Id,
                song.ArtistId,
                song.VietnameseName,
                song.SimplifiedChineseName,
                song.TraditionalChineseName,
                song.PinyinName,
                song.Duration
             }
        };
        var requestBody = new ValueRange() { Values = row };
        var request = SheetsService.Spreadsheets.Values.Append(requestBody, SPREADSHEET_ID, PLAYINGSONG_RANGE);
        request.ValueInputOption = AppendValueInputOption.RAW;
        var response = await request.ExecuteAsync();
        return response.TableRange;
    }

    [HttpPut("/television/playingsong/update")]
    public async Task<string> UpdatePlayingSong([FromBody] Song song)
    {
        var row = new List<IList<object?>>()
        {
            new List<object?>()
            {
                song.Id,
                song.ArtistId,
                song.VietnameseName,
                song.SimplifiedChineseName,
                song.TraditionalChineseName,
                song.PinyinName,
                song.Duration
             }
        };
        var requestBody = new ValueRange() { Values = row };
        var request = SheetsService.Spreadsheets.Values
            .Update(requestBody, SPREADSHEET_ID, PLAYINGSONG_RANGE);
        request.ValueInputOption = UpdateValueInputOption.RAW;
        var response = await request.ExecuteAsync();
        return response.UpdatedRange;
    }

    [HttpGet("/television/messages/get")]
    public async Task<IList<Message>> GetMessages()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(SPREADSHEET_ID, MESSAGE_RANGE).ExecuteAsync();
        var messages = new List<Message>();
        foreach (var row in responseBody.Values)
        {
            var message = new Message()
            {
                Id = row[0].GetString(),
                Content = row[1].GetString(),
                User = new User()
                {
                    Id = row[2].GetString(),
                    Name = row[3].GetString()
                }
            };
            messages.Add(message);
        }
        return messages;
    }

    [HttpPost("/television/messages/append")]
    public async Task<string> AppendMessage([FromBody] Message message)
    {
        var row = new List<IList<object?>>()
        {
            new List<object?>()
            {
                message.Id,
                message.Content,
                message.User?.Id,
                message.User?.Name
             }
        };
        var requestBody = new ValueRange() { Values = row };
        var request = SheetsService.Spreadsheets.Values
            .Append(requestBody, SPREADSHEET_ID, MESSAGE_RANGE);
        request.ValueInputOption = AppendValueInputOption.RAW;
        var response = await request.ExecuteAsync();
        return response.TableRange;
    }
}