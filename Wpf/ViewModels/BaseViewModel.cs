using Library.Enums;
using Library.Models;
using Library.Providers;
using Library.Utils;
using System.Collections.Generic;
using System.ComponentModel;
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
        OnPropertyChanged(nameof(Playlist));
    }
}
