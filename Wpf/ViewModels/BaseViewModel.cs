using Library.Enums;
using Library.Models;
using Library.Providers;
using Library.Utils;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;

namespace Wpf.ViewModels;

public class BaseViewModel : INotifyPropertyChanged
{
    public event PropertyChangedEventHandler? PropertyChanged;
    protected void OnPropertyChanged([CallerMemberName] string? propertyName = null)
        => PropertyChanged?.Invoke(this, new(propertyName));

    public readonly List<Song> Songs;
    public List<Message>? Messages;

    public List<Song> Playlist { get; }
    public Song PlayingSong { get; protected set; }
    public Language Language { get; set; } = Language.Vietnamese;

    private int messageCount = 0;

    protected BaseViewModel()
    {
        Playlist = new();
        Songs = Provider.Songs;

        Fill();
        PlayingSong = Songs.GetRandom();
    }
    
    protected virtual void Fill()
    {
        Song song;
        while (Playlist.Count < 15)
        {
            song = Songs.GetRandom();
            if (!Playlist.Contains(song))
            {
                Playlist.Add(song);
            }
        }
    }

    public virtual void Play()
    {
        PlayingSong = Playlist[0];
        Playlist[0].Users.Clear();
        Playlist.RemoveAt(0);
        Fill();
        OnPropertyChanged(nameof(Playlist));
        OnPropertyChanged(nameof(PlayingSong));
    }

    public virtual void Update()
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
        OnPropertyChanged(nameof(Playlist));
    }
}
