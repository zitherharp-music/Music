using System.Windows;
using System.Windows.Controls;
using Wpf.ViewModels;

namespace Wpf.Views.Main;

public partial class MainControl : Window
{
    public MainControl()
    {
        InitializeComponent();
        mStart.Click += delegate
        {
            var visibleScreen = mVisibleScreen.SelectedItem as ComboBoxItem;
            if (visibleScreen is not null)
            {
                var mainWindow = new MainWindow();
                mainWindow.Title += " - " + visibleScreen.Name;                
                switch (visibleScreen.Name)
                {
                    case "Main":
                        //mainWindow.DataContext = MainViewModel.Instance;
                        //mainWindow.mMainScreen.Visibility = Visibility.Visible;
                        break;
                    case "Vertical":
                        //mainWindow.DataContext = VerticalViewModel.Instance;
                        //mainWindow.mVerticalScreen.Visibility = Visibility.Visible;
                        break;
                    case "Horizontal":
                        //mainWindow.DataContext = HorizontalViewModel.Instance;
                        //mainWindow.mHorizontalScreen.Visibility = Visibility.Visible;
                        break;
                }
                mainWindow.Show();
                mainWindow.Closed += delegate { Close(); };
            }
            Hide();
        };
    }
}