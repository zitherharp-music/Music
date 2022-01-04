namespace Library.Models;

public class Spreadsheet
{
    public const string Music = "1znQOtTDJz0UqDs0uB2MQZV3wN0l_J0TrU44d9chH2SI";
    public const string AudioRange = "Audio!A1:G";
    public const string ArtistRange = "Artist!A1:F";

    #region The indices of column
    // Music
    public const int PrimaryId = 0;
    public const int ReferenceId = 1;
    public const int VietnameseName = 2;
    public const int SimplifiedChineseName = 3;
    public const int TraditionalChineseName = 4;
    public const int PinyinName = 5;
    public const int VietnameseDescription = 6;
    public const int SimplifiedChineseDescription = 7;
    public const int TraditionalChineseDescription = 8;
    // Audio
    public const int Duration = 6;
    #endregion
}