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
            var selectedWindow = mVisibleWindow.SelectedItem as ComboBoxItem;
            if (selectedWindow is not null)
            {
                var mainWindow = new MainWindow();
                switch (selectedWindow.Name)
                {
                    case "Horizontal":
                        mainWindow.Title += " - " + selectedWindow.Name;
                        mainWindow.DataContext = HorizontalViewModel.Instance;
                        mainWindow.mHorizontalWindow.Visibility = Visibility.Visible;
                        break;
                }
                mainWindow.Show();
                mainWindow.Closed += delegate { Close(); };
            }
            Hide();
        };
    }
}