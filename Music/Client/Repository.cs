using Music.Shared.Cores;
using Music.Shared.Models;
using System.Net.Http.Json;

namespace Music.Client;

internal class Repository 
{
    private static IList<Artist>? artists;
    public static IList<Artist> Artists => artists ??= 
        Spreadsheet.HttpClient.GetFromJsonAsync<IList<Artist>>("apis/music/artists/get").Result ??
        throw new NullReferenceException();

    private static IList<Song>? songs;
    public static IList<Song> Songs => songs ??=
        Spreadsheet.HttpClient.GetFromJsonAsync<IList<Song>>("apis/music/songs/get").Result ?? 
        throw new NullReferenceException();

    private static IList<Music.Shared.Models.Short>? shorts;
    public static IList<Music.Shared.Models.Short> Shorts => shorts ??=
        Spreadsheet.HttpClient.GetFromJsonAsync<IList<Music.Shared.Models.Short>>("apis/music/shorts/get").Result ??
        throw new NullReferenceException();
}   