namespace ZitherHarp.Music.Shared.Cores;

public abstract partial class Youtube : Spreadsheet
{
    public new string Path => base.Path + Id + ".mp4";

    public void Load()
    {
        if (File.Exists(Path)) return;
        if (!Repository.IsNetworkConnected()) return;
        var video = VideoLibrary.YouTube.Default.GetVideo(GetShareUrl());
        File.WriteAllBytes(Path, video.GetBytes());
    }

    public void Release() => File.Delete(Path);
}