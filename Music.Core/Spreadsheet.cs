using Music.Core.Models;
using System.Text.Json;
using static Music.Core.Spreadsheet.Convert;

namespace Music.Core;

public class Spreadsheet
{
    public class Api
    {
        public string? Range { get; init; }

        public string? MajorDimension { get; init; }

        public IList<IList<object>>? Values { get; init; }
    }

    public class Json
    {
        public IDictionary<string, string> Id { get; init; }

        public IDictionary<string, string> Range { get; init; }

        public IDictionary<string, IDictionary<string, int>> Column { get; init; }

        public IList<string> Key { get; init; }
    }

    public static class Convert
    {
        public static IList<IList<object>> GetValues(string spreadsheetId, string range)
        {
            var key = JsonValues.Key[Random.Shared.Next(JsonValues.Key.Count)];
            var url = $"https://sheets.googleapis.com/v4/spreadsheets/{ spreadsheetId }/values/{ range }?key={ key }";
            var jsonString = HttpClient.GetStringAsync(url).Result;
            var responseBody = JsonSerializer.Deserialize<Api>(jsonString);
            return responseBody?.Values ?? throw new NullReferenceException(nameof(responseBody));
        }

        public static List<Song> ToSongs(IList<IList<object>> values)
        {
            Song song;
            var songs = new List<Song>();
            var columns = JsonValues.Column["audio"];
            foreach (var value in values)
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

        public static List<Artist> ToArtists(IList<IList<object>> values)
        {
            Artist artist;
            var artists = new List<Artist>();
            var columns = JsonValues.Column["artist"];
            foreach (var value in values)
            {
                artist = new Artist()
                {
                    Id = value[columns["id"]].ToString(),
                    VietnameseName = value[columns["vietnameseName"]].ToString(),
                    ChineseName = value[columns["chineseName"]].ToString()
                };
                artists.Add(artist);
            }
            return artists;
        }
    }

    public static readonly HttpClient HttpClient = new();
    public static readonly JsonSerializerOptions JsonOptions = new()
    {
        PropertyNameCaseInsensitive = true
    };

    private static Json? jsonValues;
    public static Json JsonValues
    {
        get
        {
            if (jsonValues is null)
            {
                using var stream = new StreamReader("spreadsheet.json");
                jsonValues = JsonSerializer.Deserialize<Json>(stream.ReadToEnd(), JsonOptions) ??
                    throw new NullReferenceException(nameof(JsonValues));
            }
            return jsonValues;
        }
    }

    private static List<Song>? songs;
    public static List<Song> Songs 
        => songs ??= ToSongs(GetValues(JsonValues.Id["music"], JsonValues.Range["audio"]));

    private static List<Artist>? artists;
    public static IList<Artist> Artists 
        => artists ??= ToArtists(GetValues(JsonValues.Id["music"], JsonValues.Range["artist"]));
}