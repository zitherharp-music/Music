using Library.Enums;
using Library.Providers;
using Library.Utils;

namespace Library.Models;

public class Song : Music
{
    public List<User> Users { get; } = new();

    private readonly string artistIds;
    private List<Artist>? artists;

    public int Duration { get; }

    public Song(string id, string artistIds, int duration)
    {
        this.id = id; 
        this.artistIds = artistIds;
        Duration = duration;
    }

    public List<Artist> Artists
    {
        get
        {
            if (artists == null)
            {
                artists = new();
                foreach (var artistId in artistIds.Split(splitCharacter))
                {
                    foreach (var artist in Provider.Artists)
                    {
                        if (artist.Id != null)
                        {
                            if (artist.Id.Equals(artistId))
                            {
                                artists.Add(artist); break;
                            }
                        }
                    }
                }
            }
            return artists;
        }
    }

    public string ToString(Language language)
        => GetName(language) + " - " + Artists.GetNames(language, " & ");
}