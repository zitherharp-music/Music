using Google.Apis.Sheets.v4.Data;
using Microsoft.AspNetCore.Mvc;
using Music.Core.Models.Television;
using Music.WebApi.Data;
using static Music.Core.Service;
using AppendValueInputOption = Google.Apis.Sheets.v4.SpreadsheetsResource.ValuesResource.AppendRequest.ValueInputOptionEnum;
using UpdateValueInputOption = Google.Apis.Sheets.v4.SpreadsheetsResource.ValuesResource.UpdateRequest.ValueInputOptionEnum;

namespace Music.WebApi.Controllers;

[ApiController]
[Route("apis/[controller]")]
public class TelevisionController : ControllerBase
{
    private readonly string id = SpreadsheetJson.Id["television"];

    [HttpGet("/television/informations/get")]
    public async Task<IList<string?>> GetInformations()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(id, SpreadsheetJson.Range["information"]).ExecuteAsync();
        var informations = new List<string?>();
        foreach (var row in responseBody.Values)
        {
            informations.Add(row[0].ToString());
        }
        return informations;
    }

    [HttpGet("/television/playlist/get")]
    public async Task<IList<Song>> GetPlaylist()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(id, SpreadsheetJson.Range["playlist"]).ExecuteAsync();
        Song song;
        var songs = new List<Song>();
        var columns = SpreadsheetJson.Column["audio"];
        foreach (var value in responseBody.Values)
        {
            song = new Song()
            {
                Id = value[columns["id"]].ToString(),
                ArtistId = value[columns["artistId"]].ToString(),
                VietnameseName = value[columns["vietnameseName"]].ToString(),
                SimplifiedChineseName = value[columns["simplifiedChineseName"]].ToString(),
                TraditionalChineseName = value[columns["traditionalChineseName"]].ToString(),
                PinyinName = value[columns["pinyinName"]].ToString(),
                Duration = Convert.ToInt32(value[columns["duration"]]),
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
            .Append(requestBody, TelevisionData.SpreadsheetId, SpreadsheetJson.Range["playlist"]);
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
            .Update(requestBody, id, SpreadsheetJson.Range["playlist"]);
        request.ValueInputOption = UpdateValueInputOption.RAW;
        var response = await request.ExecuteAsync();
        return response.UpdatedRange;
    }

    [HttpGet("/television/playingsong/get")]
    public async Task<Song> GetPlayingSong()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(id, SpreadsheetJson.Range["playingsong"]).ExecuteAsync();
        return (Song)responseBody.Values.ToSongs();
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
            .Append(requestBody, id, SpreadsheetJson.Range["playingsong"]);
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
            .Update(requestBody, id, SpreadsheetJson.Range["playingsong"]);
        request.ValueInputOption = UpdateValueInputOption.RAW;
        var response = await request.ExecuteAsync();
        return response.UpdatedRange;
    }

    [HttpGet("/television/messages/get")]
    public async Task<IList<Message>> GetMessages()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(id, SpreadsheetJson.Range["message"]).ExecuteAsync();
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
            .Append(requestBody, id, SpreadsheetJson.Range["message"]);
        request.ValueInputOption = AppendValueInputOption.RAW;
        var response = await request.ExecuteAsync();
        return response.TableRange;
    }
}