using Music.Core.Extensions;
using Music.Core.Models;
using System.Text.Json;

namespace Music.Core.Services;

public class ClientService : BaseService
{
    private static readonly HttpClient httpClient = new();

    private static JsonElement.ArrayEnumerator GetValues(string sheetName, string tableName)
    {
        var id = SpreadsheetElement.GetString("id", sheetName);
        var key = SpreadsheetElement.GetProperty("key").EnumerateArray().ToArray()[0].GetString();
        var url = $"https://sheets.googleapis.com/v4/spreadsheets/{ id }/values/{ tableName }?key={ key }";
        var jsonString = httpClient.GetStringAsync(url).Result;
        var jsonElement = (JsonElement?)JsonSerializer.Deserialize<object>(jsonString);
        return jsonElement?.GetProperty("values").EnumerateArray() ?? throw new NullReferenceException(nameof(jsonElement));
    }

    private static List<Song>? songs;
    public static List<Song> Songs
    {
        get
        {
            if (songs is null)
            {
                songs = GetSongs(GetValues("music", "audio"));
            }
            return songs;
        }
    }
}