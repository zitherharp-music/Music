namespace ZitherHarp.Music.Shared.Cores;

public abstract partial class Youtube : Spreadsheet
{
    public int Duration { get; init; }

    public string GetImageUrl(Image image)
        => $"https://i.ytimg.com/vi/{Id}/{image.ToString().ToLower()}.jpg";

    public string GetShareUrl(bool isEmbed = false)
        => !isEmbed ? $"https://youtu.be/{Id}" : $"https://www.youtube.com/embed/{Id}";
}
