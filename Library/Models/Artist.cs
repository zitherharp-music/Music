using Library.Providers;

namespace Library.Models;

public class Artist : Music
{
    private List<Song>? songs;

    public Artist(string id)
    {
        this.id = id;
    }

    public List<Song> Songs
    {
        get
        {
            if (songs == null)
            {
                songs = new();
                foreach (var song in Provider.Songs)
                {
                    foreach (var artist in song.Artists)
                    {
                        if (artist.Id == null) continue;
                        if (artist.Id.Equals(id)) songs.Add(song); continue;
                    }
                }
            }
            return songs;
        }
    }
}
