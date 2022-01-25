using Google.Apis.Auth.OAuth2;
using Google.Apis.Services;
using Google.Apis.Sheets.v4;
using Google.Apis.Util.Store;
using static Music.Shared.Cores.Spreadsheet;

namespace Music.Server.Datas;

internal class Spreadsheet
{
    public static readonly Json MusicSpreadsheet = Items[nameof(Music).ToLower()];

    private static SheetsService? service;
    public static SheetsService Service
    {
        get
        {
            if (service is null)
            {
                using var stream = new FileStream("Properties/credentials.json", FileMode.Open, FileAccess.Read);
                var credential = GoogleWebAuthorizationBroker.AuthorizeAsync(
                    GoogleClientSecrets.FromStream(stream).Secrets,
                    new[] { SheetsService.Scope.Spreadsheets }, "user",
                    CancellationToken.None, new FileDataStore("Properties/token.json", true)).Result;
                service = new SheetsService(new BaseClientService.Initializer()
                {
                    HttpClientInitializer = credential,
                    ApplicationName = "Zither Harp Music"
                });
            }
            return service;
        }
    }
}