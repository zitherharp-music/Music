using Music.Shared.Cores;
using Music.Shared.Enums;

namespace Music.Shared;

public static class Util
{
    public static T GetRandom<T>(this IList<T> musics) where T : Spreadsheet
    {
        return musics[Random.Shared.Next(musics.Count)];
    }

    public static string GetNames<T>(this IList<T> musics, Language language, 
        string splitCharacter = " & ") where T : Spreadsheet 
    {
        var names = string.Empty; 
        if (musics.Count == 0) return names;
        foreach (var music in musics)
        {
            names += music.GetName(language) + splitCharacter;
        }
        return names[0..^splitCharacter.Length];
    }
}