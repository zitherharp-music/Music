using Google.Apis.Auth.OAuth2;
using Google.Apis.Services;
using Google.Apis.Sheets.v4;
using Google.Apis.Util.Store;
using Music.Core.Extensions;
using Music.Core.Models;

namespace Music.Core.Services;

public class ServerService : BaseService
{
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

    public static List<Song> Songs
    {
        get
        {
            var id = SpreadsheetElement.GetString("id", "music");
            var range = SpreadsheetElement.GetString("range", "audio");
            var valueRange = SheetsService.Spreadsheets.Values.Get(id, range).Execute();
            return GetSongs(valueRange.Values);
        }
    }

    private static List<Artist>? artists;
    public static List<Artist> Artists
    {
        get
        {
            if (artists == null)
            {
                var id = SpreadsheetElement.GetString("id", "music");
                var range = SpreadsheetElement.GetString("range", "audio");
                var valueRange = SheetsService.Spreadsheets.Values.Get(id, range).Execute();
                artists = GetArtists(valueRange.Values);
            }
            return artists;
        }
    }
}