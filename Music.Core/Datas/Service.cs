using Google.Apis.Auth.OAuth2;
using Google.Apis.Services;
using Google.Apis.Sheets.v4;
using Google.Apis.Util.Store;

namespace Music.Core;

public static class Service
{
    private static SheetsService? sheetsService;
    public static SheetsService SheetsService
    {
        get
        {
            if (sheetsService == null)
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
}