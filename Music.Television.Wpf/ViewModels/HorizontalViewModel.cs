namespace Music.Television.Wpf.ViewModels;

internal class HorizontalViewModel : MainViewModel
{
    private static HorizontalViewModel? instance;
    public static HorizontalViewModel Instance => instance ??= new();
}