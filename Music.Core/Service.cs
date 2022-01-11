using Google.Apis.Auth.OAuth2;
using Google.Apis.Services;
using Google.Apis.Sheets.v4;
using Google.Apis.Util.Store;
using Music.Core.Models;
using System.Text.Json;

namespace Music.Core;

public static class Service
{
    public static readonly HttpClient HttpClient = new();

    private static SheetsService? sheetsService;
    public static SheetsService SheetsService
    {
        get
        {
            if (sheetsService is null)
            {
                using var stream = new FileStream("credentials.json", FileMode.Open, FileAccess.Read);
                var credential = GoogleWebAuthorizationBroker.AuthorizeAsync(
                    GoogleClientSecrets.FromStream(stream).Secrets,
                    new[] { SheetsService.Scope.Spreadsheets }, "user",
                    CancellationToken.None, new FileDataStore("token.json", true)).Result;
                sheetsService = new SheetsService(new BaseClientService.Initializer()
                {
                    HttpClientInitializer = credential,
                    ApplicationName = "Zither Harp Music"
                });
            }
            return sheetsService;
        }
    }

    public static IList<Song> ToSongs(this IList<IList<object>> values)
    {
        Song song;
        var songs = new List<Song>();
        var columns = Spreadsheet.JsonValues.Column["audio"];
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
        return songs;
    }

    public static IList<Artist> ToArtists(this IList<IList<object>> values)
    {
        Artist artist;
        var artists = new List<Artist>();
        var columns = Spreadsheet.JsonValues.Column["artist"];
        foreach (var value in values)
        {
            artist = new Artist()
            {
                Id = value[columns["id"]].ToString(),
                VietnameseName = value[columns["vietnameseName"]].ToString(),
                SimplifiedChineseName = value[columns["simplifiedChineseName"]].ToString(),
                TraditionalChineseName = value[columns["traditionalChineseName"]].ToString(),
                PinyinName = value[columns["pinyinName"]].ToString(),
            };
            artists.Add(artist);
        }
        return artists;
    }
}