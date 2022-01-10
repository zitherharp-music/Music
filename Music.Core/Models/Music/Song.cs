namespace Music.Core.Models.Music;

public class Song : Music
{
    public string? ArtistId { get; init; }
    public int Duration { get; init; }

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
                    foreach (var artist in Repository.Instance.Artists)
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