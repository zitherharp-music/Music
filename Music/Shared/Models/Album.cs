using Music.Shared.Cores;

namespace Music.Shared.Models;

public class Album : QQMusic
{
    public string? SongId { get; init; }

    public override string GetImageUrl(Image image)
        => $"https://y.qq.com/music/photo_new/T002R{ (int)image }x{ (int)image }M000{ Id }.jpg";
}