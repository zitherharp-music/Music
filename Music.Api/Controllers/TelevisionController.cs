using Google.Apis.Sheets.v4.Data;
using Microsoft.AspNetCore.Mvc;
using Music.Shared.Models;
using static Music.Shared.Cores.Spreadsheet;
using static Music.Api.Service;
using AppendValueInputOption = Google.Apis.Sheets.v4.SpreadsheetsResource.ValuesResource.AppendRequest.ValueInputOptionEnum;
using UpdateValueInputOption = Google.Apis.Sheets.v4.SpreadsheetsResource.ValuesResource.UpdateRequest.ValueInputOptionEnum;

namespace Music.Api.Controllers;

[ApiController]
[Route("[controller]")]
public class TelevisionController : ControllerBase
{
    private readonly string id = JsonValues.Id["television"];

    [HttpGet("/television/informations/get")]
    public async Task<IList<string?>> GetInformations()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(id, JsonValues.Range["information"]).ExecuteAsync();
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
            .Get(id, JsonValues.Range["playlist"]).ExecuteAsync();
        Song song;
        var songs = new List<Song>();
        var columns = JsonValues.Column["audio"];
        foreach (var value in responseBody.Values)
        {
            song = new Song()
            {
                Id = value[columns["id"]].ToString(),
                ArtistId = value[columns["artistId"]].ToString(),
                VietnameseName = value[columns["vietnameseName"]].ToString(),
                ChineseName = value[columns["chineseName"]].ToString(),
                Duration = (int)value[columns["duration"]],
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
                song.ChineseName,
                song.Duration
            };
            rows.Add(row);
        }
        var requestBody = new ValueRange() { Values = rows };
        var request = SheetsService.Spreadsheets.Values
            .Append(requestBody, id, JsonValues.Range["playlist"]);
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
                song.ChineseName,
                song.Duration
            };
            rows.Add(row);
        }
        var requestBody = new ValueRange() { Values = rows };
        var request = SheetsService.Spreadsheets.Values
            .Update(requestBody, id, JsonValues.Range["playlist"]);
        request.ValueInputOption = UpdateValueInputOption.RAW;
        var response = await request.ExecuteAsync();
        return response.UpdatedRange;
    }

    [HttpGet("/television/playingsong/get")]
    public async Task<Song> GetPlayingSong()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(id, JsonValues.Range["playlist"]).ExecuteAsync();
        return Serializer.Deserialize<IList<Song>>(responseBody.Values)[0];
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
                song.ChineseName,
                song.Duration
             }
        };
        var requestBody = new ValueRange() { Values = row };
        var request = SheetsService.Spreadsheets.Values
            .Append(requestBody, id, JsonValues.Range["playingsong"]);
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
                song.ChineseName,
                song.Duration
             }
        };
        var requestBody = new ValueRange() { Values = row };
        var request = SheetsService.Spreadsheets.Values
            .Update(requestBody, id, JsonValues.Range["playingsong"]);
        request.ValueInputOption = UpdateValueInputOption.RAW;
        var response = await request.ExecuteAsync();
        return response.UpdatedRange;
    }

    [HttpGet("/television/messages/get")]
    public async Task<IList<Message>> GetMessages()
    {
        var responseBody = await SheetsService.Spreadsheets.Values
            .Get(id, JsonValues.Range["message"]).ExecuteAsync();
        var messages = new List<Message>();
        foreach (var row in responseBody.Values)
        {
            var message = new Message()
            {
                Id = row[0].ToString(),
                Content = row[1].ToString(),
                User = new User()
                {
                    Id = row[2].ToString(),
                    Name = row[3].ToString()
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
            .Append(requestBody, id, JsonValues.Range["message"]);
        request.ValueInputOption = AppendValueInputOption.RAW;
        var response = await request.ExecuteAsync();
        return response.TableRange;
    }
}