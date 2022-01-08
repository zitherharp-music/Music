using Google.Apis.Sheets.v4.Data;
using Microsoft.AspNetCore.Mvc;
using Music.WebApi.Models.Music;
using Music.WebApi.Models.Television;
using Music.WebApi.Data;
using static Music.WebApi.Service;
using static Music.WebApi.Data.AudioData;
using static Music.WebApi.Data.MusicData;
using static Music.WebApi.Data.TelevisionData;
using AppendValueInputOption = Google.Apis.Sheets.v4.SpreadsheetsResource.ValuesResource.AppendRequest.ValueInputOptionEnum;
using UpdateValueInputOption = Google.Apis.Sheets.v4.SpreadsheetsResource.ValuesResource.UpdateRequest.ValueInputOptionEnum;

namespace Music.WebApi.Controllers;

[ApiController]
[Route("apis/[controller]")]
public class TelevisionController : ControllerBase
{
    [HttpGet("/television/informations/get")]
    public async Task<IList<string>> GetInformations()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(TelevisionData.SpreadsheetId, InformationRange).ExecuteAsync();
        var informations = new List<string>();
        foreach (var row in responseBody.Values)
        {
            informations.Add(row[Id].GetString());
        }
        return informations;
    }

    [HttpGet("/television/playlist/get")]
    public async Task<IList<Song>> GetPlaylist()
    {
        var songs = new List<Song>();
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(TelevisionData.SpreadsheetId, PlaylistRange).ExecuteAsync();
        foreach (var row in responseBody.Values)
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
        var request = SheetsService.Spreadsheets.Values
            .Append(requestBody, TelevisionData.SpreadsheetId, PlaylistRange);
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
        var request = SheetsService.Spreadsheets.Values
            .Update(requestBody, TelevisionData.SpreadsheetId, PlaylistRange);
        request.ValueInputOption = UpdateValueInputOption.RAW;
        var response = await request.ExecuteAsync();
        return response.UpdatedRange;
    }

    [HttpGet("/television/playingsong/get")]
    public async Task<Song> GetPlayingSong()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(TelevisionData.SpreadsheetId, PlayingSongRange).ExecuteAsync();
        foreach (var row in responseBody.Values)
        {
            return new Song()
            {
                Id = row[Id].GetString(),
                ArtistId = row[ArtistId].GetString(),
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
        var request = SheetsService.Spreadsheets.Values
            .Append(requestBody, TelevisionData.SpreadsheetId, PlayingSongRange);
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
            .Update(requestBody, TelevisionData.SpreadsheetId, PlayingSongRange);
        request.ValueInputOption = UpdateValueInputOption.RAW;
        var response = await request.ExecuteAsync();
        return response.UpdatedRange;
    }

    [HttpGet("/television/messages/get")]
    public async Task<IList<Message>> GetMessages()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(TelevisionData.SpreadsheetId, MessageRange).ExecuteAsync();
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
            .Append(requestBody, TelevisionData.SpreadsheetId, MessageRange);
        request.ValueInputOption = AppendValueInputOption.RAW;
        var response = await request.ExecuteAsync();
        return response.TableRange;
    }
}