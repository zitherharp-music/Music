namespace Music.Core.Datas;

using Music.Core.Enums;
using Music.Core.Models;

public static class Extension
{
    public static string GetImageUrl(this Music music, ImageQuality imageQuality) => imageQuality switch
    {
        ImageQuality.Small or ImageQuality.Medium or ImageQuality.Large
            => $"https://y.qq.com/music/photo_new/T001R{ (int)imageQuality }x{ (int)imageQuality }M000{ music.Id }.jpg",
        _ => $"https://i.ytimg.com/vi/{ music.Id }/{ imageQuality.ToString().ToLower() }.jpg"
    };

    public static string? GetName(this Music music, Language language) => language switch
    {
        Language.Pinyin => music.PinyinName,
        Language.Vietnamese => music.VietnameseName,
        Language.SimplifiedChinese => music.SimplifiedChineseName,
        Language.TraditionalChinese => music.TraditionalChineseName,
        _ => null
    };

    public static string GetNames<T>(this List<T> musics, 
        Language language, string splitCharacter = "/") where T : Music
    {
        var names = string.Empty;
        if (musics.Count == 0) return names;
        foreach (var music in musics) names += music.GetName(language) + splitCharacter;
        return names[0..^splitCharacter.Length];
    }

    public static T GetRandom<T>(this IList<T> musics) where T : Music
    {
        return musics[Random.Shared.Next(0, musics.Count)];
    }
}