using Music.Cores;
using Music.Enums;

namespace Music.Television.Wpf.ViewModels;

public class MainViewModel : BaseViewModel
{
    private static MainViewModel? instance;
    public static MainViewModel Instance => instance ??= new();

    public string? SongName1 => Playlist[0].GetName(Language);
    public string? SongName2 => Playlist[1].GetName(Language);
    public string? SongName3 => Playlist[2].GetName(Language);

    public int SongVote1 => Playlist[0].Users.Count * 10 + 10;
    public int SongVote2 => Playlist[1].Users.Count * 10 + 10;
    public int SongVote3 => Playlist[2].Users.Count * 10 + 10;

    public string SongImage1 => Playlist[0].GetImageUrl(Youtube.Image.MQDefault);
    public string SongImage2 => Playlist[1].GetImageUrl(Youtube.Image.MQDefault);
    public string SongImage3 => Playlist[2].GetImageUrl(Youtube.Image.MQDefault);

    public string PlayingSongImage => PlayingSong.GetImageUrl(Youtube.Image.SDDefault);

    public string PlayingSongDetail
    {
        get
        {
            var detail = "Đang phát: " + PlayingSong.GetName(Language.Vietnamese)
            + "\nThể hiện: " + PlayingSong.GetArtists().GetNames(Language.Vietnamese);
            if (PlayingSong.Users.Count > 0)
            {
                detail = "Khán giả yêu cầu nhanh nhất: " + PlayingSong.Users[0].Name + "\n" + detail;
            }
            return detail;
        }
    }

    public string PlaylistString
    {
        get
        {
            var playlistString = string.Empty;
            for (int i = 0; i < 15; i++)
            {
                playlistString += Playlist[i].ToString(Language) + "; ";
            }
            return playlistString;
        }
    }

    public string? VoteSyntax => "Soạn tin: ZHM " + Songs.GetRandom().GetName(Language);

    public override void Play()
    {
        base.Play();
        OnPropertyChanged(nameof(SongName1));
        OnPropertyChanged(nameof(SongName2));
        OnPropertyChanged(nameof(SongName3));
        OnPropertyChanged(nameof(SongVote1));
        OnPropertyChanged(nameof(SongVote2));
        OnPropertyChanged(nameof(SongVote3));
        OnPropertyChanged(nameof(SongImage1));
        OnPropertyChanged(nameof(SongImage2));
        OnPropertyChanged(nameof(SongImage3));
        OnPropertyChanged(nameof(PlaylistString));
        OnPropertyChanged(nameof(PlayingSongImage));
        OnPropertyChanged(nameof(PlayingSongDetail));
    }

    public override void Update()
    {
        base.Update();
        OnPropertyChanged(nameof(SongName1));
        OnPropertyChanged(nameof(SongName2));
        OnPropertyChanged(nameof(SongName3));
        OnPropertyChanged(nameof(SongVote1));
        OnPropertyChanged(nameof(SongVote2));
        OnPropertyChanged(nameof(SongVote3));
        OnPropertyChanged(nameof(SongImage1));
        OnPropertyChanged(nameof(SongImage2));
        OnPropertyChanged(nameof(SongImage3));
        OnPropertyChanged(nameof(PlaylistString));
    }
}
