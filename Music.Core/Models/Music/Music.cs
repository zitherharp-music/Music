namespace Music.Core.Models.Music;

public class Music
{
    protected const string splitCharacter = "/";

    public string? Id { get; init; }
    public string? PinyinName { get; init; }
    public string? VietnameseName { get; init; }
    public string? SimplifiedChineseName { get; init; }
    public string? TraditionalChineseName { get; init; }
}