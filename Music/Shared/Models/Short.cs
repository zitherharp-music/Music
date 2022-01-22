using Music.Shared.Cores;

namespace Music.Shared.Models;

public class Short : Youtube
{
    public new class Api
    {
        public int Rank { get; init; }
        public int Duration { get; init; }
        public string? Author { get; init; }
        public string? Title { get; init; }
        public string? Value { get; init; }
        public string? Link { get; init; }
        public string? Img_Url { get; init; }
    }

    public string? SongId { get; init; }

    private IList<Song>? songs;
    public IList<Song> GetSongs()
    {
        if (songs is null)
        {
            songs = new List<Song>();
            if (SongId is null) return songs;
            foreach (var songId in SongId.Split(splitCharacter))
            {
                foreach (var song in Repository.Songs)
                {
                    if (songId.Equals(song.Id)) songs.Add(song); break;
                }    
            }    
        }    
        return songs;
    }
}