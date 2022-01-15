using Music.Cores;

namespace Music.Models;

public class Artist : QQMusic
{
    public override string GetImageUrl(Image image)
        => $"https://y.qq.com/music/photo_new/T001R{ (int)image }x{ (int)image }M000{ Id }.jpg";

    private IList<Song>? songs;
    public IList<Song> GetSongs()
    {
        if (songs is null)
        {
            songs = new List<Song>();
            foreach (var song in Repository.Songs)
            {
                if (song.ArtistId is null) continue;
                foreach (var artistId in song.ArtistId.Split(splitCharacter))
                {
                    if (Id is null) continue;
                    if (Id.Equals(artistId)) songs.Add(song); continue;
                }
            }
        }    
        return songs;
    }
}