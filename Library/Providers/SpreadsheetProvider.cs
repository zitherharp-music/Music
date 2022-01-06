using Library.Enums;
using Library.Models;
using Newtonsoft.Json;

namespace Library.Providers;

public partial class Provider
{
    private const int PrimaryId = 0;
    private const int ReferenceId = 1;
    private const int VietnameseName = 2;
    private const int SimplifiedChineseName = 3;
    private const int TraditionalChineseName = 4;
    private const int PinyinName = 5;
    private const int VietnameseDescription = 6;
    private const int SimplifiedChineseDescription = 7;
    private const int TraditionalChineseDescription = 8;
    private const int Duration = 6;

    private static dynamic GetValues(string tableName)
    {
        var url = $"https://sheets.googleapis.com/v4/spreadsheets/" +
            $"{ Resource.SpreadsheetId }/values/{ tableName }?key={ Resource.SpreadsheetApiKey }";
        var jsonString = new HttpClient().GetStringAsync(url).Result;
        dynamic jsonObject = JsonConvert.DeserializeObject(jsonString) ?? throw new ArgumentNullException(jsonString);
        return jsonObject.values ?? throw new ArgumentNullException(jsonObject);
    }

    private static string GetString(dynamic value) => value.ToString() ?? string.Empty;
    private static int GetInt32(dynamic value) => int.TryParse(GetString(value), out int i) ? i : 0;

    private static List<Song>? songs;
    public static List<Song> Songs
    {
        get
        {
            if (songs == null)
            {
                Song song;
                songs = new();
                foreach (var values in GetValues("audio"))
                {
                    if (values.Count == 0) continue;
                    song = new(GetString(values[PrimaryId]),
                        GetString(values[ReferenceId]), GetInt32(values[Duration]));
                    song.SetName(Language.Vietnamese, GetString(values[VietnameseName]));
                    song.SetName(Language.SimplifiedChinese, GetString(values[SimplifiedChineseName]));
                    song.SetName(Language.TraditionalChinese, GetString(values[TraditionalChineseName]));
                    song.SetName(Language.Pinyin, GetString(values[PinyinName]));
                    songs.Add(song);
                }
            }
            return songs;
        }
    }

    private static List<Artist>? artists;
    public static List<Artist> Artists
    {
        get
        {
            if (artists == null)
            {
                Artist artist;
                artists = new();
                foreach (var values in GetValues(nameof(Artist)))
                {
                    if (values.Count == 0) continue;
                    artist = new(GetString(values[PrimaryId]));
                    artist.SetName(Language.Vietnamese, GetString(values[VietnameseName]));
                    artist.SetName(Language.SimplifiedChinese, GetString(values[SimplifiedChineseName]));
                    artist.SetName(Language.TraditionalChinese, GetString(values[TraditionalChineseName]));
                    artist.SetName(Language.Pinyin, GetString(values[PinyinName]));
                    artists.Add(artist);

                }
            }
            return artists;
        }
    }
}
