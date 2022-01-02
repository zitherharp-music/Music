using Library.Utils;
using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Threading;
using Wpf.ViewModels;
using Wpf.Views.Main;

namespace Wpf.Views.Horizontal;

public partial class HorizontalControl : UserControl
{
    private HorizontalViewModel? viewModel;

    public HorizontalControl()
    {
        InitializeComponent();
        Loaded += delegate
        {
            InitializeData();
            InitializeTimer();
        };
    }

    private void InitializeData()
    {
        if (Window.GetWindow(this) is MainWindow mainWindow)
        {
            DataContext = mainWindow.DataContext;
            viewModel = DataContext as HorizontalViewModel;
        }
    }

    private void InitializeTimer()
    {
        DispatcherTimer timer;

        // The timer of vote syntax
        timer = new() { Interval = TimeSpan.FromSeconds(2) };
        timer.Tick += (s, e) =>
        {
            mVoteSyntax.Text = viewModel?.Songs.GetVoteSyntax(viewModel.Language);
        };
        timer.Start();

        // The timer of information and playlist
        timer = new() { Interval = TimeSpan.FromMilliseconds(1) };
        timer.Tick += (s, e) =>
        {
            mPlaylist.Margin = new(mPlaylist.Margin.Left - 2,
                mPlaylist.Margin.Top, mPlaylist.Margin.Right, mPlaylist.Margin.Bottom);
            mInformation.Margin = new(mInformation.Margin.Left - 2,
                mInformation.Margin.Top, mInformation.Margin.Right, mInformation.Margin.Bottom);
            if (mPlaylist.Margin.Left < -6000)
            {
                mPlaylist.Margin = new(ActualWidth,
                    mPlaylist.Margin.Top, mPlaylist.Margin.Right, mPlaylist.Margin.Bottom);
            }
            if (mInformation.Margin.Left < -4000)
            {
                mInformation.Margin = new(ActualWidth - mVoteSyntax.RenderSize.Width,
                    mInformation.Margin.Top, mInformation.Margin.Right, mInformation.Margin.Bottom);
            }
        };
        timer.Start();
    }

    private void Button_Click(object sender, RoutedEventArgs e)
    {
        new HorizontalWindow().ShowDialog();
    }
}