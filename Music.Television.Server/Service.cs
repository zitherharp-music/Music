using Library.Models;

internal class Service
{
    private static string GetString(object value)
        => value.ToString() ?? throw new ArgumentNullException(nameof(value));

    private static int GetInteger(object value)
        => Convert.ToInt32(value);

    public static List<Song> Songs { get; } = new();
    public static List<Artist> Artists { get; } = new();

    //public static async Task Initialize()
    //{
    //    Console.WriteLine("Verifying user credential...");
    //    using var stream = new FileStream("credentials.json", FileMode.Open, FileAccess.Read);
    //    var credential = GoogleWebAuthorizationBroker.AuthorizeAsync(
    //        GoogleClientSecrets.FromStream(stream).Secrets,
    //        new[] { SheetsService.Scope.SpreadsheetsReadonly }, "user",
    //        CancellationToken.None, new FileDataStore("token.json", true)).Result;

    //    Console.WriteLine("Initializing spreadsheet service...");
    //    var sheetsService = new SheetsService(new BaseClientService.Initializer()
    //    {
    //        HttpClientInitializer = credential,
    //        ApplicationName = "Google SpeadSheet"
    //    });

    //    Console.WriteLine("Loading songs...");
    //    var songRange = await sheetsService.Spreadsheets.Values
    //        .Get(Spreadsheet.Music, AudioRange).ExecuteAsync();
    //    foreach (var row in songRange.Values)
    //    {
    //        var song = new Song(GetString(row[PrimaryId]),
    //            GetString(row[ReferenceId]), GetInteger(row[Duration]));
    //        song.SetName(Language.Pinyin, GetString(row[PinyinName]));
    //        song.SetName(Language.Vietnamese, GetString(row[VietnameseName]));
    //        song.SetName(Language.SimplifiedChinese, GetString(row[SimplifiedChineseName]));
    //        song.SetName(Language.TraditionalChinese, GetString(row[TraditionalChineseName]));
    //        Songs.Add(song);
    //    }

    //    Console.WriteLine("Loading artists...");
    //    var artistRange = await sheetsService.Spreadsheets.Values
    //        .Get(Spreadsheet.Music, ArtistRange).ExecuteAsync(); 
    //    foreach (var row in artistRange.Values)
    //    {
    //        var artist = new Artist(GetString(row[PrimaryId]));
    //        artist.SetName(Language.Pinyin, GetString(row[PinyinName]));
    //        artist.SetName(Language.Vietnamese, GetString(row[VietnameseName]));
    //        artist.SetName(Language.SimplifiedChinese, GetString(row[SimplifiedChineseName]));
    //        artist.SetName(Language.TraditionalChinese, GetString(row[TraditionalChineseName]));
    //        Artists.Add(artist);
    //    }

    //    Console.WriteLine("Successfully!");
    //}  
}