using Music.Shared.Cores;

namespace Music.Client.Wpf.Television.ViewModels;

internal class VerticalViewModel : HoriverticalViewModel
{
    private static VerticalViewModel? instance;
    public static VerticalViewModel Instance => instance ??= new();

    public string SongImage1 => Playlist[0].GetImageUrl(Youtube.Image.MQDefault);
    public string SongImage2 => Playlist[1].GetImageUrl(Youtube.Image.MQDefault);
    public string SongImage3 => Playlist[2].GetImageUrl(Youtube.Image.MQDefault);

    public override void Play()
    {
        base.Play();
        OnPropertyChanged(nameof(SongImage1));
        OnPropertyChanged(nameof(SongImage2));
        OnPropertyChanged(nameof(SongImage3));
    }

    public override void Update()
    {
        base.Update();
        OnPropertyChanged(nameof(SongImage1));
        OnPropertyChanged(nameof(SongImage2));
        OnPropertyChanged(nameof(SongImage3));
    }
}