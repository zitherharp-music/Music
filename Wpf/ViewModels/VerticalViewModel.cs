namespace Wpf.ViewModels;

public class VerticalViewModel : MainViewModel
{
    private static VerticalViewModel? instance;
    public static VerticalViewModel Instance => instance ??= new();
}