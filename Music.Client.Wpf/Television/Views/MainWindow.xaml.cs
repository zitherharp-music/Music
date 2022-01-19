using Music.Client.Wpf.Television.Views.Horizontal;
using Music.Client.Wpf.Television.Views.Main;
using System.Windows;

namespace Music.Client.Wpf.Television.Views;

public partial class MainWindow : Window
{
    public MainWindow()
    {
        InitializeComponent();
        mMainContainer.Content = new HorizontalPage(isHD: true);
    }
}