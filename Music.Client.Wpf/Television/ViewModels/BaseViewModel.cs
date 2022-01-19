using Music.Shared;
using Music.Shared.Cores;
using Music.Shared.Enums;
using Music.Shared.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Runtime.CompilerServices;

namespace Music.Client.Wpf.Television.ViewModels;

public class BaseViewModel : INotifyPropertyChanged
{
    public event PropertyChangedEventHandler? PropertyChanged;
    protected void OnPropertyChanged([CallerMemberName] string? propertyName = null)
        => PropertyChanged?.Invoke(this, new(propertyName));

    protected readonly IList<Song> songs;

    public IList<Song> Playlist { get; }
    public Song PlayingSong { get; protected set; }
    public Language Language { get; set; } = Language.Vietnamese;

    protected BaseViewModel()
    {
        Playlist = new List<Song>();
        songs = Spreadsheet.Repository.Songs;

        Fill();
        PlayingSong = songs.GetRandom();
    }

    protected virtual void Fill()
    {
        Song song;
        while (Playlist.Count < 15)
        {
            song = songs[Random.Shared.Next(songs.Count)];
            if (Playlist.Contains(song)) continue;
            Playlist.Add(song);
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