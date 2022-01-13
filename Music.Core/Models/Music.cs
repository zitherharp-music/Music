namespace Music.Core.Models;

public class Music
{
    protected const string splitCharacter = "/";

    public string? Id { get; init; }
    public string? VietnameseName { get; init; }
    public string? ChineseName { get; init; }
}