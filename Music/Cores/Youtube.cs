namespace Music.Cores;

public class Youtube : Spreadsheet
{
    public enum Image
    {
        Default = 120,
        MQDefault = 320,
        HQDefault = 480,
        SDDefault = 640,
        MaxResDefault = 1280
    }

    public string GetImageUrl(Image image)
        => $"https://i.ytimg.com/vi/{ Id }/{ image.ToString().ToLower() }.jpg";

    public string? ArtistId { get; init; }
}