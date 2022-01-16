using Music.Shared.Cores;
using System.Text.Json.Serialization;

namespace Music.Shared.Models;

public class Song : Youtube
{
    public int Duration { get; init; }

    [JsonIgnore]
    public List<User> Users { get; } = new();

    private IList<Artist>? artists;
    public IList<Artist> GetArtists()
    {
        if (artists is null)
        {
            artists = new List<Artist>();
            if (ArtistId is null) return artists;
            foreach (var artistId in ArtistId.Split(splitCharacter))
            {
                foreach (var artist in Repository.Artists)
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