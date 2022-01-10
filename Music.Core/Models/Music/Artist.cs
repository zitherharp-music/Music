namespace Music.Core.Models.Music;

public class Artist : Music
{
    public string GetImageUrl(Image.QQMusic image)
        => $"https://y.qq.com/music/photo_new/T001R{ (int)image }x{ (int)image }M000{ Id }.jpg";

    private List<Song>? songs;
    public List<Song> Songs
    {
        get
        {
            if (songs is null)
            {
                songs = new();
                foreach (var song in Repository.Songs)
                {
                    foreach (var artist in song.Artists)
                    {
                        if (artist.Id is null) continue;
                        if (artist.Id.Equals(Id)) songs.Add(song); continue;
                    }
                }
            }
            return songs;
        }
    }
}