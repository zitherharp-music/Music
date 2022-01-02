using Library.Enums;
using Library.Models;
using System.Collections.Generic;

namespace Wpf.ViewModels;

public interface IViewModel 
{
    public Song PlayingSong { get; }

    public List<Song> Playlist { get; }

    /// <summary>
    /// The language of view model
    /// </summary>
    public Language Language { get; set; }

    /// <summary>
    /// Play the song
    /// </summary>
    public void Play();

    /// <summary>
    /// Update the data
    /// </summary>
    public void Update();
}