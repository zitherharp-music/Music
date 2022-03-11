namespace ZitherHarp.Music.Television;

public partial class App : Application
{
    public App()
    {
        InitializeComponent();
        MainPage = new MainPage();
    }

    protected override Window CreateWindow(IActivationState activationState)
        => new(new NavigationPage(new MainPage() { Title = "Zither Harp Music: Television" }));
}