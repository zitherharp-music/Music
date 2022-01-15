using Music.Television.Wpf.ViewModels;
using System.Windows;

namespace Music.Television.Wpf.Views.Horizontal;

public partial class HorizontalControl : Window
{
    public HorizontalControl()
    {
        InitializeComponent();
        DataContext = HorizontalViewModel.Instance;
    }
}