using ZitherHarp.Music.Shared.Cores;

namespace ZitherHarp.Music.Shared.Models;

public partial class Audio : Youtube
{
    private string? artistId;
    private string? chineseLyric, vietnameseLyric;

    public record User(string Id, string Name);

    public List<User> Users { get; } = new();

    public string ToString(Language language, string splitChar = Character.Combine)
        => GetName(language) + Character.Concat + GetArtists().GetNames(language, splitChar);

    public string? GetLyric(Language language)
    {
        return language switch
        {
            Language.Chinese => chineseLyric,
            Language.Vietnamese => vietnameseLyric,
            _ => null
        };
    }

    public IList<Artist> GetArtists()
    {
        var artists = new List<Artist>();
        if (artistId is not null)
        {
            foreach (var id in artistId.Split(Character.Split))
            {
                foreach (var artist in Artist.Repository)
                {
                    if (artist.Key == id)
                    {
                        artists.Add(artist.Value); break;
                    }
                }
            }
        }    
        return artists;
    }
}
