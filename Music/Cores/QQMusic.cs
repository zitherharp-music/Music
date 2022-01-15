namespace Music.Cores;

public abstract class QQMusic : Spreadsheet
{
    public enum Image
    {
        Small = 300,
        Medium = 500,
        Large = 800
    }

    public abstract string GetImageUrl(Image image);
}