using Music.Shared.Enums;
using Music.Shared.Models;
using System.Text.Json;

namespace Music.Shared.Cores;

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
        public string[] Key { get; init; } = Array.Empty<string>();

        public Dictionary<string, string> Id { get; init; } = new();

        public Dictionary<string, string> Range { get; init; } = new();

        public Dictionary<string, IDictionary<string, int>> Column { get; init; } = new();        
    }

    public class Repository
    {
        private static IList<Song>? songs;
        public static IList<Song> Songs
            => songs ??= Serializer.Deserialize<IList<Song>>(
                Serializer.GetValues(JsonValues.Id["music"], JsonValues.Range["audio"]));

        private static IList<Artist>? artists;
        public static IList<Artist> Artists 
            => artists ??= Serializer.Deserialize<IList<Artist>>(
                Serializer.GetValues(JsonValues.Id["music"], JsonValues.Range["artist"]));

        private static IList<Short>? shorts;
        public static IList<Short> Shorts
            => shorts ??= Serializer.Deserialize<IList<Short>>(
                Serializer.GetValues(JsonValues.Id["music"], JsonValues.Range["short"]));
    }

    public class Serializer
    {
        public static IList<IList<object>> GetValues(string id, string range)
        {
            var key = JsonValues.Key[Random.Shared.Next(JsonValues.Key.Length)];
            var url = $"https://sheets.googleapis.com/v4/spreadsheets/{ id }/values/{ range }?key={ key }";
            var jsonString = HttpClient.GetStringAsync(url).Result;
            var responseBody = JsonSerializer.Deserialize<Api>(jsonString, JsonOptions);
            return responseBody?.Values ?? throw new NullReferenceException(nameof(responseBody));
        }

        public static T Deserialize<T>(IList<IList<object>> values)
        {
            IDictionary<string, int> columns;
            switch (typeof(T).GenericTypeArguments[0].Name)
            {
                case nameof(Song):
                    Song song;
                    var songs = new List<Song>();
                    columns = JsonValues.Column["audio"];
                    foreach (var value in values)
                    {
                        song = new Song()
                        {
                            Id = value[columns["id"]].ToString(),
                            ArtistId = value[columns["artistId"]].ToString(),
                            VietnameseName = value[columns["vietnameseName"]].ToString(),
                            ChineseName = value[columns["chineseName"]].ToString(),
                            Duration = int.TryParse(value[columns["duration"]].ToString(), out int i) ? i : 0
                        };
                        songs.Add(song);
                    }
                    return (T)(IList<Song>)songs;
                case nameof(Video):
                    Video video;
                    var videos = new List<Video>();
                    columns = JsonValues.Column["video"];
                    foreach (var value in values)
                    {
                        video = new Video()
                        {
                            Id = value[columns["id"]].ToString(),
                            ArtistId = value[columns["artistId"]].ToString(),
                            VietnameseName = value[columns["vietnameseName"]].ToString(),
                            ChineseName = value[columns["chineseName"]].ToString(),
                            Duration = int.TryParse(value[columns["duration"]].ToString(), out int i) ? i : 0
                        };
                        videos.Add(video);
                    }
                    return (T)(IList<Video>)videos;
                case nameof(Artist):
                    Artist artist;
                    var artists = new List<Artist>();
                    columns = JsonValues.Column["artist"];
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
                    return (T)(IList<Artist>)artists;
                case nameof(Short):
                    Short @short;
                    var shorts = new List<Short>();
                    columns = JsonValues.Column["short"];
                    foreach (var value in values)
                    {
                        @short = new Short()
                        {
                            Id = value[columns["id"]].ToString(),
                            ArtistId = value[columns["artistId"]].ToString(),
                            //SongId = !string.IsNullOrEmpty(value[columns["songId"]].ToString()) ? value[columns["songId"]].ToString() : ""
                        };
                        shorts.Add(@short);
                    }
                    return (T)(IList<Short>)shorts;
            }
            throw new FormatException(nameof(T));
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

    #region Properties
    protected const string splitCharacter = "/";

    public string? Id { get; init; }
    public string? VietnameseName { get; init; }
    public string? ChineseName { get; init; }
    #endregion

    public string? GetName(Language language) => language switch
    {
        Language.Vietnamese => VietnameseName,
        Language.SimplifiedChinese => ChineseName,
        _ => null
    };
}