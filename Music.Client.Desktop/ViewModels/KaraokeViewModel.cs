namespace Music.Client.Desktop.ViewModels;

public class KaraokeViewModel
{
    private static KaraokeViewModel? instance;
    public static KaraokeViewModel Instance => instance ??= new();
}