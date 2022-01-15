namespace Wpf.ViewModels;

public class BasicViewModel
{
    private static BasicViewModel? instance;
    public static BasicViewModel Instance => instance ??= new();
}