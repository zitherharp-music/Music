using System.Text.Json.Serialization;

namespace Music.Core.Models;

public class Song : Music
{
    public string? ArtistId { get; init; }
    public int Duration { get; init; }

    [JsonIgnore]
    public List<User> Users { get; } = new();

    public string GetImageUrl(Image.Youtube image)
        => $"https://i.ytimg.com/vi/{ Id }/{ image.ToString().ToLower() }.jpg";

    private List<Artist>? artists;
    public List<Artist> Artists
    {
        get
        {
            if (artists is null)
            {
                artists = new();
                if (ArtistId is null) return artists;
                foreach (var artistId in ArtistId.Split(splitCharacter))
                {
                    foreach (var artist in Repository.Artists)
                    {
                        if (artist.Id is not null)
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
}