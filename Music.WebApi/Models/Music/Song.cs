namespace Music.WebApi.Models.Music;

public class Song : Music
{
    public string? ArtistId { get; init; }
    public int Duration { get; init; }
}