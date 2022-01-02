using Library.Enums;
using Library.Models;

namespace Library.Utils;

public static partial class Util
{
    private static readonly Random random = new();
    private static string? voteSyntax, numberId;

    public static string GetNames<T>(this List<T> musics, Language language, string splitCharacter) where T : Music
    {
        var names = string.Empty;
        if (musics.Count == 0) return names;
        foreach (var music in musics)
        {
            names += music.GetName(language) + splitCharacter;
        }
        return names[0..^splitCharacter.Length];
    }

    public static T GetRandom<T>(this IList<T> musics) where T : Music
    {
        return musics[random.Next(0, musics.Count)];
    }

    public static string? GetNumberId(this string? id)
    {
        if (id == null)
        {
            throw new ArgumentNullException(nameof(id));
        }
        numberId = string.Empty;
        foreach (var character in id)
        {
            numberId += character - 45;
        }
        return numberId;
    }

    public static string? GetVoteSyntax(this IList<Song> songs, Language language)
    {
        switch (language)
        {
            case Language.Vietnamese:
                voteSyntax = "Soạn tin: ZHM " + GetNumberId(songs.GetRandom().Id);
                break;
        }
        return voteSyntax;
    }

    public static int FindIndex(this IList<Song> songs, Language language, string name)
    {
        for (int i = 0; i < songs.Count; i++)
        {
            if (name.Equals(songs[i].GetName(language))) return i;
        }
        return -1;
    }
}
