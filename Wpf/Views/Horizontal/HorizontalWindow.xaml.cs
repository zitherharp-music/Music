using Library.Providers;
using System;
using System.Windows;

namespace Wpf.Views.Horizontal;

public partial class HorizontalWindow : Window
{
    public HorizontalWindow()
    {
        InitializeComponent();
        mStart.Click += async (s, e) =>
        {
            try
            {
                await Provider.InitializeService(mLiveStreamingId.Text);
                Hide();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, ex.Source, MessageBoxButton.OK, MessageBoxImage.Error);
            }
        };
    }
}