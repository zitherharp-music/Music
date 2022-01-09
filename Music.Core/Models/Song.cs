using static Music.Core.Services.ServerService;
using System.Text.Json.Serialization;

namespace Music.Core.Models;

public class Song : Music
{
    public string? ArtistId { get; init; }
    public int Duration { get; init; }

    private List<Artist>? artists;
    public List<Artist> Artists
    {
        get
        {
            if (artists == null)
            {
                if (ArtistId is null) throw new NullReferenceException();
                artists = new();
                foreach (var artistId in ArtistId.Split(splitCharacter))
                {
                    foreach (var artist in Instance.Artists)
                    {
                        if (artist.Id is null) continue;
                        if (artist.Id.Equals(artistId))
                        {
                            artists.Add(artist); break;
                        }
                    }
                }
            }
            return artists;
        }
    }

    [JsonIgnore]
    public List<User> Users { get; } = new();
}