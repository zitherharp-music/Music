namespace Wpf.ViewModels;

public class BasicViewModel : BaseViewModel
{
    private static BasicViewModel? instance;
    public static BasicViewModel Instance => instance ??= new();

    public string PlaylistString
    {
        get
        {
            var playlistString = string.Empty;
            foreach (var song in Playlist)
            {
                playlistString += song.ToString(Language) + "\n";
            }
            return playlistString.TrimEnd();
        }
    }
}