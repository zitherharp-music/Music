using Google.Apis.Auth.OAuth2;
using Google.Apis.Services;
using Google.Apis.Sheets.v4;
using Google.Apis.Util.Store;
using Microsoft.AspNetCore.Mvc;
using Music.WebApi.Models;

namespace Music.WebApi.Controllers;

[ApiController]
[Route("[controller]")]
public class MusicController : ControllerBase
{
    #region The detail of spreadsheet
    protected const string SPREADSHEET_ID = "1znQOtTDJz0UqDs0uB2MQZV3wN0l_J0TrU44d9chH2SI";
    protected const string SONG_RANGE = "Audio!A1:G";
    protected const string ARTIST_RANGE = "Artist!A1:F";
    #endregion

    #region The indices of column
    // Music
    public const int PrimaryId = 0;
    public const int ReferenceId = 1;
    public const int VietnameseName = 2;
    public const int SimplifiedChineseName = 3;
    public const int TraditionalChineseName = 4;
    public const int PinyinName = 5;
    // Audio
    public const int Duration = 6;
    #endregion

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

    [HttpGet("/music/songs")]
    public async Task<IList<Song>> GetSongs()
    {
        if (SheetsService is null) throw new NullReferenceException(nameof(SheetsService));
        var songs = new List<Song>();
        var songRange = await SheetsService.Spreadsheets.Values
            .Get(SPREADSHEET_ID, SONG_RANGE).ExecuteAsync();
        foreach (var row in songRange.Values)
        {
            var song = new Song()
            {
                Id = row[PrimaryId].GetString(),
                ArtistId = row[ReferenceId].GetString(),
                VietnameseName = row[VietnameseName].GetString(),
                SimplifiedChineseName = row[SimplifiedChineseName].GetString(),
                TraditionalChineseName = row[TraditionalChineseName].GetString(),
                PinyinName = row[PinyinName].GetString(),
                Duration = row[Duration].GetInteger()
            };
            songs.Add(song);
        }
        return songs;
    }

    [HttpGet("/music/artists")]
    public async Task<IList<Artist>> GetArtists()
    {
        if (SheetsService is null) throw new NullReferenceException(nameof(SheetsService));
        var artists = new List<Artist>();
        var artistRange = await SheetsService.Spreadsheets.Values
            .Get(SPREADSHEET_ID, ARTIST_RANGE).ExecuteAsync();
        foreach (var row in artistRange.Values)
        {
            var artist = new Artist()
            {
                Id = row[PrimaryId].GetString(),
                PinyinName = row[PinyinName].GetString(),
                VietnameseName = row[VietnameseName].GetString(),
                SimplifiedChineseName = row[SimplifiedChineseName].GetString(),
                TraditionalChineseName = row[TraditionalChineseName].GetString()
            };
            artists.Add(artist);
        }
        return artists;
    }
}