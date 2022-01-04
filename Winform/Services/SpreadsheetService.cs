﻿using Google.Apis.Auth.OAuth2;
using Google.Apis.Services;
using Google.Apis.Sheets.v4;
using Google.Apis.Util.Store;
using Library.Enums;
using Library.Models;
using static Library.Models.Spreadsheet;

namespace Winform.Services;

internal class SpreadsheetService
{
    public static List<Song> Songs { get; } = new();
    public static List<Artist> Artists { get; } = new();

    private SheetsService? sheetsService;

    private void Service()
    {
        using var stream = new FileStream("credentials.json", FileMode.Open, FileAccess.Read);
        var credential = GoogleWebAuthorizationBroker.AuthorizeAsync(
            GoogleClientSecrets.FromStream(stream).Secrets,
            new[] { SheetsService.Scope.SpreadsheetsReadonly }, "user",
            CancellationToken.None, new FileDataStore("token.json", true)).Result;
        sheetsService = new SheetsService(new BaseClientService.Initializer()
        {
            HttpClientInitializer = credential,
            ApplicationName = "Zither Harp Music: Television"
        });
        // TODO: Get songs
        var songRange = sheetsService.Spreadsheets.Values.Get(Spreadsheet.Music, AudioRange).Execute();
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
        var artistRange = sheetsService.Spreadsheets.Values.Get(Spreadsheet.Music, ArtistRange).Execute();
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

    private static string GetString(object value)
       => value.ToString() ?? throw new ArgumentNullException(nameof(value));

    private static int GetInteger(object value)
        => Convert.ToInt32(value);
}