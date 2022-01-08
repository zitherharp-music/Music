using Library.Enums;
using Library.Utils;
using Library.Models;
using Library.Providers;

namespace Wpf.ViewModels;

public class MainViewModel : BaseViewModel
{
    public string? SongName1 => Playlist[0].GetName(Language);
    public string? SongName2 => Playlist[1].GetName(Language);
    public string? SongName3 => Playlist[2].GetName(Language);

    public int SongVote1 => Playlist[0].Users.Count * 10 + 10;
    public int SongVote2 => Playlist[1].Users.Count * 10 + 10;
    public int SongVote3 => Playlist[2].Users.Count * 10 + 10;

    public string PlayingSongImage => PlayingSong.GetPhotoUrl(PhotoQuality.MaxResDefault);

    public string PlayingSongDetail
    {
        get
        {
            var detail = "Đang phát: " + PlayingSong.GetName(Language.Vietnamese)
            + "\nThể hiện: " + PlayingSong.Artists.GetNames(Language.Vietnamese, " & ");
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

    private int messageCount = 0;

    public override void Play()
    {
        base.Play();
        OnPropertyChanged(nameof(SongName1));
        OnPropertyChanged(nameof(SongName2));
        OnPropertyChanged(nameof(SongName3));
        OnPropertyChanged(nameof(SongVote1));
        OnPropertyChanged(nameof(SongVote2));
        OnPropertyChanged(nameof(SongVote3));
        OnPropertyChanged(nameof(PlaylistString));
        OnPropertyChanged(nameof(PlayingSongImage));
        OnPropertyChanged(nameof(PlayingSongDetail));
    }

    public override void Update()
    {
        if (messageCount >= Provider.Messages.Count) return;
        Messages = Provider.Messages;
        foreach (var message in Messages.GetRange(messageCount, Messages.Count - messageCount))
        {
            if (message.User == null) continue;
            if (message.Content == null) continue;
            if (!message.Content.StartsWith("ZHM ")) continue;
            //if (Playlist.Where(song => song.Users.Contains(message.User)).Count() > 2) return;
            Song? song;
            var content = message.Content[4..].TrimEnd().ToLower();
            // TODO: Find the song
            var index = Playlist.FindIndex(song =>
            {
                var songName = song.GetName(Language);
                return songName is not null && songName.ToLower().Equals(content);
            });
            if (index == -1)
            {
                song = Songs.Find(song =>
                {
                    var songName = song.GetName(Language);
                    return songName is not null && songName.ToLower().Equals(content);
                });
                if (song == null) continue;
            }
            else
            {
                song = Playlist[index];
                // TODO: Check if user has voted this song
                if (song.Users.Find(user =>
                    user.Id != null && user.Id.Equals(message.Id)) != null) continue;
                Playlist.RemoveAt(index);
            }
            song.Users.Add(message.User);
            // TODO: Sort the song
            for (int i = 0; i < Playlist.Count; i++)
            {
                if (song.Users.Count > Playlist[i].Users.Count)
                {
                    Playlist.Insert(i, song);
                    break;
                }
            }
        }
        messageCount = Messages.Count;

        base.Update();
        OnPropertyChanged(nameof(SongName1));
        OnPropertyChanged(nameof(SongName2));
        OnPropertyChanged(nameof(SongName3));
        OnPropertyChanged(nameof(SongVote1));
        OnPropertyChanged(nameof(SongVote2));
        OnPropertyChanged(nameof(SongVote3));
        OnPropertyChanged(nameof(PlaylistString));
    }
}
