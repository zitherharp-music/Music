namespace Music.WebApi.Models;

public class Song : Music
{
    public string? ArtistId { get; init; }
    public int Duration { get; init; }
}