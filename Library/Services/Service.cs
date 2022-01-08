using Google.Apis.Auth.OAuth2;
using Google.Apis.Services;
using Google.Apis.Sheets.v4;
using Google.Apis.Util.Store;
using Library.Enums;
using Library.Models;

namespace Library.Services;

public class Service
{
    private static Service? instance;
    public static Service Instance => instance ??= new();

    private const int PrimaryId = 0;
    private const int ReferenceId = 1;
    private const int VietnameseName = 2;
    private const int SimplifiedChineseName = 3;
    private const int TraditionalChineseName = 4;
    private const int PinyinName = 5;
    private const int Duration = 6;

    private readonly SheetsService? sheetsService;

    private static string GetString(object value)
        => value.ToString() ?? throw new ArgumentNullException(nameof(value));

    private static int GetInteger(object value)
        => Convert.ToInt32(value);

    public static List<Song> Songs { get; } = new();
    public static List<Artist> Artists { get; } = new();

    private Service()
    {
        using var stream = new FileStream("credentials.json", FileMode.Open, FileAccess.Read);
        var credential = GoogleWebAuthorizationBroker.AuthorizeAsync(
            GoogleClientSecrets.FromStream(stream).Secrets,
            new[] { SheetsService.Scope.Spreadsheets }, "user",
            CancellationToken.None, new FileDataStore("token.json", true)).Result;
        sheetsService = new SheetsService(new BaseClientService.Initializer()
        {
            HttpClientInitializer = credential,
            ApplicationName = Resource.ApplicationName
        });
        // TODO: Get songs
        var songRange = sheetsService.Spreadsheets.Values
            .Get(Resource.SpreadsheetId, "Audio!A1:G").Execute();
        foreach (var row in songRange.Values)
        {
            var song = new Song(GetString(row[PrimaryId]),
                GetString(row[ReferenceId]), GetInteger(row[Duration]));
            song.SetName(Language.Pinyin, GetString(row[PinyinName]));
            song.SetName(Language.Vietnamese, GetString(row[VietnameseName]));
            song.SetName(Language.SimplifiedChinese, GetString(row[SimplifiedChineseName]));
            song.SetName(Language.TraditionalChinese, GetString(row[TraditionalChineseName]));
            Songs.Add(song);
        }
        // TODO: Get artists
        var artistRange = sheetsService.Spreadsheets.Values
            .Get(Resource.SpreadsheetId, "Artist!A1:F").Execute();
        foreach (var row in artistRange.Values)
        {
            var artist = new Artist(GetString(row[PrimaryId]));
            artist.SetName(Language.Pinyin, GetString(row[PinyinName]));
            artist.SetName(Language.Vietnamese, GetString(row[VietnameseName]));
            artist.SetName(Language.SimplifiedChinese, GetString(row[SimplifiedChineseName]));
            artist.SetName(Language.TraditionalChinese, GetString(row[TraditionalChineseName]));
            Artists.Add(artist);
        }
    }

    private readonly List<Message> messages = new();
    public List<Message> Messages
    {
        get
        {
            if (sheetsService is not null)
            {
                messages.Clear();
                var messageRange = sheetsService.Spreadsheets.Values
                    .Get(Resource.SpreadsheetId, "Message!A1:G").Execute();
                foreach (var row in messageRange.Values)
                {
                    var message = new Message()
                    {
                        User = new User()
                        {
                            Id = "",
                            Name = ""
                        },
                        Id = "",
                        Content = ""
                    };
                    Messages.Add(message);
                }
            }    
            return messages;
        }
    }
}