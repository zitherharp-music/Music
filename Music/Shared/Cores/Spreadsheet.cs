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
        public string? Id { get; init; }

        public string[] Key { get; init; } = Array.Empty<string>();

        public Dictionary<string, string> Range { get; init; } = new();

        public Dictionary<string, IDictionary<string, int>> Column { get; init; } = new();
    }

    public class Repository
    {
        private static readonly Json music = Items["music"];

        private static IList<Artist>? artists;
        public static IList<Artist> Artists
            => artists ??= Serializer.Deserialize<IList<Artist>>(
                Serializer.Deserialize(music, music.Range[nameof(Artist).ToLower()]));

        private static IList<Audio>? audios; 
        public static IList<Audio> Audios
            => audios ??= Serializer.Deserialize<IList<Audio>>(
                Serializer.Deserialize(music, music.Range[nameof(Audio).ToLower()]));

        private static IList<Short>? shorts;
        public static IList<Short> Shorts
            => shorts ??= Serializer.Deserialize<IList<Short>>(
                Serializer.Deserialize(music, music.Range[nameof(Short).ToLower()]));
    }

    public class Serializer
    {
        public static IList<IList<object>> Deserialize(Json json, string range)
        {
            var key = json.Key[Random.Shared.Next(json.Key.Length)];
            var url = $"https://sheets.googleapis.com/v4/spreadsheets/{ json.Id }/values/{ range }?key={ key }";
            var jsonString = httpClient.GetStringAsync(url).Result;
            var responseBody = JsonSerializer.Deserialize<Api>(jsonString, jsonOptions);
            return responseBody?.Values ?? throw new NullReferenceException(nameof(responseBody));
        }

        public static T Deserialize<T>(IList<IList<object>> values)
        {
            IDictionary<string, int> columns;
            switch (typeof(T).GenericTypeArguments[0].Name)
            {
                case nameof(Audio):
                    Audio song;
                    var songs = new List<Audio>();
                    columns = Items["music"].Column["audio"];
                    foreach (var value in values)
                    {
                        song = new Audio()
                        {
                            Id = value[columns["id"]].ToString(),
                            ArtistId = value[columns["artistId"]].ToString(),
                            VietnameseName = value[columns["vietnameseName"]].ToString(),
                            ChineseName = value[columns["chineseName"]].ToString(),
                            Duration = int.TryParse(value[columns["duration"]].ToString(), out int i) ? i : 0
                        };
                        songs.Add(song);
                    }
                    return (T)(IList<Audio>)songs;
                case nameof(Video):
                    Video video;
                    var videos = new List<Video>();
                    columns = Items["music"].Column["video"];
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
                    columns = Items["music"].Column["artist"];
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
                    columns = Items["music"].Column["short"];
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

    private static readonly HttpClient httpClient = new();
    private static readonly JsonSerializerOptions jsonOptions = new()
    {
        PropertyNameCaseInsensitive = true
    };

    private static IDictionary<string, Json>? items;
    public static IDictionary<string, Json> Items
    {
        get
        {
            if (items is null)
            {
                using var stream = new StreamReader("Properties/spreadsheets.json");
                items = JsonSerializer.Deserialize<IDictionary<string, Json>>(
                    stream.ReadToEnd(), jsonOptions) ?? throw new NullReferenceException(nameof(Items));
            }
            return items;
        }
    }

    #region Properties
    protected const string splitChar = "/";

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