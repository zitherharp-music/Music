namespace Music.Core.Models.Television;

public class Song : Music.Song
{
    public List<User> Users { get; } = new();
}