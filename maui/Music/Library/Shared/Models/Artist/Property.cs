using ZitherHarp.Music.Shared.Cores;

namespace ZitherHarp.Music.Shared.Models;

public partial class Artist : QQMusic
{
    public override string GetImageUrl(Image image)
        => $"https://y.qq.com/music/photo_new/T001R{(int)image}x{(int)image}M000{Id}.jpg";
}
