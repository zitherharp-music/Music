using Music.Shared.Cores;

namespace Music.Shared.Models;

public class Artist : QQMusic
{
    public override string GetImageUrl(Image image)
        => $"https://y.qq.com/music/photo_new/T001R{ (int)image }x{ (int)image }M000{ Id }.jpg";

    private IList<Audio>? songs;
    public IList<Audio> GetSongs()
    {
        if (songs is null)
        {
            songs = new List<Audio>();
            foreach (var song in Repository.Audios)
            {
                if (song.ArtistId is null) continue;
                foreach (var artistId in song.ArtistId.Split(splitChar))
                {
                    if (Id is null) continue;
                    if (Id.Equals(artistId)) songs.Add(song); continue;
                }
            }
        }    
        return songs;
    }

    private IList<Short>? shorts;
    public IList<Short> GetShorts()
    {
        if (shorts is null)
        {
            shorts = new List<Short>();
            foreach (var @short in Repository.Shorts)
            {
                if (@short.ArtistId is null) continue;
                foreach (var artistId in @short.ArtistId.Split(splitChar))
                {
                    if (Id is null) continue;
                    if (Id.Equals(artistId)) shorts.Add(@short); continue;
                }
            }
        }
        return shorts;
    }
}