namespace Music.Core.Datas;

public class Spreadsheet
{
    public static readonly Dictionary<string, string> Id = new()
    {
        { "Music", "1znQOtTDJz0UqDs0uB2MQZV3wN0l_J0TrU44d9chH2SI" },
        { "Television", "15XE1J5YPKyCoEHHJabM7IDQeK_wgSnKbWVRvn9eqVVc" }
    };

    public static readonly Dictionary<string, string> Range = new()
    {
        { "Audio", "Audio!A1:G" },
        { "Artist", "Artist!A1:F" }
    };

    public static readonly Dictionary<string, int> Column = new()
    {
        { "Id", 0 },
        { "ArtistId", 1 },
        { "VietnameseName", 2 },
        { "SimplifiedChineseName", 3},
        { "TraditionalChineseName", 4 },
        { "PinyinName", 5 },
        { "Duration", 6 }
    };
}