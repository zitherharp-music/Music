using Music.Core.Extensions;
using Music.Core.Models;
using System.Text.Json;

namespace Music.Core.Services;

public class BaseService
{
    private static JsonElement? spreadsheetElement;
    protected static JsonElement SpreadsheetElement
    {
        get
        {
            if (spreadsheetElement is null)
            {
                using var stream = new StreamReader("spreadsheet.json");
                spreadsheetElement = (JsonElement?)JsonSerializer.Deserialize<object>(stream.ReadToEnd());
            }
            return spreadsheetElement ?? throw new NullReferenceException(nameof(spreadsheetElement));
        }
    }

    private static readonly JsonElement audioColumn = SpreadsheetElement.GetProperty("column", "audio");
    private static readonly JsonElement artistColumn = SpreadsheetElement.GetProperty("column", "arrtist");

    protected static List<Song> GetSongs(dynamic values)
    {
        Song song;
        var songs = new List<Song>();
        foreach (var value in values)
        {
            song = new Song()
            {
                Id = value[audioColumn.GetInt32("id")].ToString(),
                ArtistId = value[audioColumn.GetInt32("artistId")].ToString(),
                VietnameseName = value[audioColumn.GetInt32("vietnameseName")].ToString(),
                SimplifiedChineseName = value[audioColumn.GetInt32("simplifiedChineseName")].ToString(),
                TraditionalChineseName = value[audioColumn.GetInt32("traditionalChineseName")].ToString(),
                PinyinName = value[audioColumn.GetInt32("pinyinName")].ToString(),
                Duration = Convert.ToInt32(value[audioColumn.GetInt32("duration")]),
            };
            songs.Add(song);
        }
        return songs;
    }

    protected static List<Artist> GetArtists(dynamic values)
    {
        Artist artist;
        var artists = new List<Artist>();
        foreach (var value in values)
        {
            artist = new Artist()
            {
                Id = value[artistColumn.GetInt32("id")].ToString(),
                VietnameseName = value[artistColumn.GetInt32("vietnameseName")].ToString(),
                SimplifiedChineseName = value[artistColumn.GetInt32("simplifiedChineseName")].ToString(),
                TraditionalChineseName = value[artistColumn.GetInt32("traditionalChineseName")].ToString(),
                PinyinName = value[artistColumn.GetInt32("pinyinName")].ToString()
            };
            artists.Add(artist);
        }
        return artists;
    }
}