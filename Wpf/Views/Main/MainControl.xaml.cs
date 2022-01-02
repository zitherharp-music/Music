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
            var selectedScreen = mVisibleScreen.SelectedItem as ComboBoxItem;
            if (selectedScreen is not null)
            {
                var mainWindow = new MainWindow();
                mainWindow.Title += " - " + selectedScreen.Name;
                switch (selectedScreen.Name)
                {
                    case "Basic":
                        mainWindow.DataContext = BasicViewModel.Instance;
                        mainWindow.mBasicScreen.Visibility = Visibility.Visible;
                        break;
                    case "Horizontal":
                        mainWindow.DataContext = HorizontalViewModel.Instance;
                        mainWindow.mHorizontalScreen.Visibility = Visibility.Visible;
                        break;
                }
                mainWindow.Show();
                mainWindow.Closed += delegate { Close(); };
            }
            Hide();
        };
    }
}