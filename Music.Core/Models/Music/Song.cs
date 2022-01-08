using System.Text.Json.Serialization;

namespace Music.Core.Models;

public class Song : Music
{
    public string? ArtistId { get; init; }
    public int Duration { get; init; }

    [JsonIgnore]
    public List<User> Users { get; } = new();
}