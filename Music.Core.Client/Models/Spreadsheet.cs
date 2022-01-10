using Music.Core.Client;
using Music.Core.Models.Music;
using Music.Core.Models.Spreadsheet;
using System.Text.Json;

namespace Music.Core.Client.Models;

public class Spreadsheet
{
    private static async Task<IList<IList<object>>> GetValues(string sheetName, string tableName)
    {
        var id = Repository.SpreadsheetJson.Id[sheetName];
        var key = Repository.SpreadsheetJson.Key[0];
        var url = $"https://sheets.googleapis.com/v4/spreadsheets/{ id }/values/{ tableName }?key={ key }";
        var jsonString = await Repository.HttpClient.GetStringAsync(url);
        var responseBody = JsonSerializer.Deserialize<SpreadsheetApi>(jsonString);
        return responseBody?.Values ?? throw new NullReferenceException(nameof(responseBody));
    }

    private static List<Song>? songs;
    public static List<Song> Songs
    {
        get
        {
            if (songs is null)
            {
                Song song;
                songs = new List<Song>();
                var values = GetValues("music", "audio").Result;
                var columns = Repository.SpreadsheetJson.Column["audio"];
                foreach (var value in values)
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
            }
            return songs;
        }
    }
}