using Google.Apis.Auth.OAuth2;
using Google.Apis.Services;
using Google.Apis.Sheets.v4;
using Google.Apis.Util.Store;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}
app.UseHttpsRedirection();

#region The detail of spreadsheet
const string MusicSpreadsheetId = "1znQOtTDJz0UqDs0uB2MQZV3wN0l_J0TrU44d9chH2SI";
const string AudioRange = "Audio!A1:G";
const string ArtistRange = "Artist!A1:F";
#endregion

#region The indices of column
// Music
const int PrimaryId = 0;
const int ReferenceId = 1;
const int VietnameseName = 2;
const int SimplifiedChineseName = 3;
const int TraditionalChineseName = 4;
const int PinyinName = 5;
// Audio
const int Duration = 6;
#endregion

SheetsService? sheetsService = null;

app.MapMethods("/", new[] { "HEAD" }, () =>
{
    using var stream = new FileStream("credentials.json", FileMode.Open, FileAccess.Read);
    var credential = GoogleWebAuthorizationBroker.AuthorizeAsync(
        GoogleClientSecrets.FromStream(stream).Secrets,
        new[] { SheetsService.Scope.SpreadsheetsReadonly }, "user",
        CancellationToken.None, new FileDataStore("token.json", true)).Result;
    sheetsService = new SheetsService(new BaseClientService.Initializer()
    {
        HttpClientInitializer = credential,
        ApplicationName = "Zither Harp Music"
    });
}).WithName("InitializeSheetsService");

static string GetString(object value)
    => value.ToString() ?? throw new ArgumentNullException(nameof(value));

static int GetInteger(object value) => Convert.ToInt32(value);

app.MapGet("/songs", async () =>
{
    if (sheetsService is null) throw new NullReferenceException(nameof(sheetsService));
    var songs = new List<Song>();
    var songRange = await sheetsService.Spreadsheets.Values.Get(MusicSpreadsheetId, AudioRange).ExecuteAsync();
    foreach (var row in songRange.Values)
    {
        var song = new Song()
        {
            Id = GetString(row[PrimaryId]),
            ArtistId = GetString(row[ReferenceId]),
            VietnameseName = GetString(row[VietnameseName]),
            SimplifiedChineseName = GetString(row[SimplifiedChineseName]),
            TraditionalChineseName = GetString(row[TraditionalChineseName]),
            PinyinName = GetString(row[PinyinName]),
            Duration = GetInteger(row[Duration])
        };
        songs.Add(song);
    }
    return songs;
}).WithName("GetSongs");

app.MapGet("/artists", async () =>
{
    if (sheetsService is null) throw new NullReferenceException(nameof(sheetsService));
    var artists = new List<Artist>();
    var artistRange = await sheetsService.Spreadsheets.Values.Get(MusicSpreadsheetId, ArtistRange).ExecuteAsync();
    foreach (var row in artistRange.Values)
    {
        var artist = new Artist()
        {
            Id = GetString(row[PrimaryId]),
            VietnameseName = GetString(row[VietnameseName]),
            SimplifiedChineseName = GetString(row[SimplifiedChineseName]),
            TraditionalChineseName = GetString(row[TraditionalChineseName]),
            PinyinName = GetString(row[PinyinName])
        };
        artists.Add(artist);
    }
    return artists;
}).WithName("GetArtists");

#region Data models
record Music
{
    public string? Id { get; init; }
    public string? PinyinName { get; init; }
    public string? VietnameseName { get; init; }
    public string? SimplifiedChineseName { get; init; }
    public string? TraditionalChineseName { get; init; }
}

record Song : Music
{
    public string? ArtistId { get; init; }
    public int Duration { get; init; }
}

record Artist : Music { }
#endregion