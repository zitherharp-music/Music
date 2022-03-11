namespace ZitherHarp.Music.Shared.Cores;

public static class Extension
{
    public static T GetRandom<T>(this IList<T> items) where T : Spreadsheet
        => items[Random.Shared.Next(0, items.Count)];

    public static string GetNames<T>(this IList<T> musics, Language language,
        string splitChar = Spreadsheet.Character.Combine) where T : Spreadsheet
    {
        var names = string.Empty;
        foreach (var music in musics)
        {
            names += music.GetName(language) + splitChar;
        }
        return names[0..^splitChar.Length];
    }
}