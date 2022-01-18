using Music.Client.Desktop;
using System.Windows;

namespace Music.Client.Windows.Television;

public partial class HorizontalControl : Window
{
    public HorizontalControl()
    {
        InitializeComponent(); 
        DataContext = HorizontalViewModel.Instance;
    }
}