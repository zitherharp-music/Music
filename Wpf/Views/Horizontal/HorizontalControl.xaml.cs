using Library.Providers;
using System;
using System.Windows;

namespace Wpf.Views.Horizontal;

public partial class HorizontalControl : Window
{
    public HorizontalControl()
    {
        InitializeComponent();
        mStart.Click += async delegate
        {
            try
            {
                await Provider.InitializeService(mLiveStreamingId.Text);
                Hide();
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message, e.Source, MessageBoxButton.OK, MessageBoxImage.Error);
            }
        };
    }
}