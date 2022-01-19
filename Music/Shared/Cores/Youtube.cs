using Music.Shared.Enums;
using Music.Shared.Models;
using System.Text.Json.Serialization;

namespace Music.Shared.Cores;

public abstract class Youtube : Spreadsheet
{
    public enum Image
    {
        Default = 120,
        MQDefault = 320,
        HQDefault = 480,
        SDDefault = 640,
        MaxResDefault = 1280
    }

    public string? ArtistId { get; init; }

    public int Duration { get; init; }

    [JsonIgnore]
    public List<User> Users { get; } = new();

    public string GetImageUrl(Image image)
        => $"https://i.ytimg.com/vi/{ Id }/{ image.ToString().ToLower() }.jpg";

    protected IList<Artist>? artists;
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

    public string ToString(Language language)
        => GetName(language) + " - " + GetArtists().GetNames(language);
}