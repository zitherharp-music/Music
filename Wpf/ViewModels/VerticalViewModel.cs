using Library.Enums;
using Library.Utils;
using System;

namespace Wpf.ViewModels;

public class VerticalViewModel : MainViewModel
{
    private static VerticalViewModel? instance;
    public static new VerticalViewModel Instance => instance ??= new();

    public string SongImage1 => Playlist[0].GetPhotoUrl(PhotoQuality.MQDefault);
    public string SongImage2 => Playlist[1].GetPhotoUrl(PhotoQuality.MQDefault);
    public string SongImage3 => Playlist[2].GetPhotoUrl(PhotoQuality.MQDefault);

    public string? VoteSyntax
    {
        get
        {
            return Language switch
            {
                Language.Vietnamese => Songs.GetRandom().Id.GetNumberId(),
                _ => throw new NotImplementedException()
            };
        }
    }    

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