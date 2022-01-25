using Music.Shared.Cores;
using Music.Shared.Models;

namespace Music.Shared.Extensions;

public static class YoutubeExtension
{
    public static IList<Artist> GetArtists(this Youtube youtube, IList<Artist> artists)
    {
        var list = new List<Artist>();
        if (youtube.ArtistId is null) return list;
        foreach (var artistId in youtube.ArtistId.Split('/'))
        {
            foreach (var artist in artists)
            {
                if (artist.Id is not null)
                {
                    if (artist.Id.Equals(artistId))
                    {
                        list.Add(artist); break;
                    }
                }
            }
        }
        return list;
    }
}