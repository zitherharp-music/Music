using Music.Core.Models.Music;
using Music.Core.Models;
using System.Text.Json;

namespace Music.Core;

public class Repository
{
    public static IList<Song> Songs { get; private set; } = new List<Song>();
    public static IList<Artist> Artists { get; private set; } = new List<Artist>();

    public static void Initialize(bool isClient)
    {
        var musicId = Service.SpreadsheetJson.Id["music"];
        var audioRange = Service.SpreadsheetJson.Range["audio"];
        var artistRange = Service.SpreadsheetJson.Range["artist"];

        if (isClient)
        {
            Songs = Get(musicId, audioRange).ToSongs();
            Artists = Get(musicId, artistRange).ToArtists();
        }    
        else
        {
            var values = Service.SheetsService.Spreadsheets.Values;
            Songs = values.Get(musicId, audioRange).Execute().Values.ToSongs();
            Artists = values.Get(musicId, artistRange).Execute().Values.ToArtists();
        }    
    }

    /// <summary>
    /// Get values from the spreadsheet
    /// (This method only uses for client service)
    /// </summary>
    /// <param name="sheetName">The name of spreadsheet</param>
    /// <param name="tableName">The name of sheet</param>
    /// <returns>A list contains all returned rows and columns</returns>
    /// <exception cref="NullReferenceException"></exception>
    private static IList<IList<object>> Get(string spreadsheetId, string range)
    {
        var key = Service.SpreadsheetJson.Key[0];
        var url = $"https://sheets.googleapis.com/v4/spreadsheets/{ spreadsheetId }/values/{ range }?key={ key }";
        var jsonString = Service.HttpClient.GetStringAsync(url).Result;
        var responseBody = JsonSerializer.Deserialize<Spreadsheet.Api>(jsonString);
        return responseBody?.Values ?? throw new NullReferenceException(nameof(responseBody));
    }
}