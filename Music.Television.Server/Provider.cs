using Library.Enums;
using Library.Models;
using Library.Utils;

internal class Provider
{
    private static Provider? instance;
    public static Provider Instance => instance ??= new();

    public static List<Song> Playlist { get; } = new();
    public static Song? PlayingSong { get; private set; }
    public static Language Language { get; set; } = Language.Vietnamese;

    public static void Initialize()
    {
        Console.WriteLine("Initializing new playlist...");
        Fill();
        Console.WriteLine("Preparing to play...");
        PlayingSong = Service.Songs.GetRandom();
        Console.WriteLine("Successfully!");
    }

    public static void Fill()
    {
        Song song;
        while (Playlist.Count < 15)
        {
            song = Service.Songs.GetRandom();
            if (!Playlist.Contains(song))
            {
                Playlist.Add(song);
            }
        }
    }

    public static void Play()
    {
        PlayingSong = Playlist[0];
        Playlist[0].Users.Clear();
        Playlist.RemoveAt(0);
        Fill();
    }
}
