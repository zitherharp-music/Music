using System.Windows.Controls;
using System;
using System.Windows;
using System.Windows.Threading;
using Music.Client.Wpf.Television.ViewModels;

namespace Music.Client.Wpf.Television.Views.Horizontal;

public partial class HorizontalPage : Page
{
    public HorizontalPage(bool isHD)
    {
        InitializeComponent();
        Loaded += delegate
        {
            #region DataContext
            var viewModel = HorizontalViewModel.Instance;
            DataContext = viewModel;

            if (Window.GetWindow(this) is MainWindow mainWindow)
            {
                mainWindow.mMainContainer.DataContext = viewModel;
                mainWindow.Title = $"{ mainWindow.Title } - Horizontal";
            }
            if (!isHD)
            {
                mScreen.Content = new ScreenPage(viewModel);
            }
            mPlayer.IsEnabled = false;
            mPlayer.Visibility = isHD ? Visibility.Visible : Visibility.Hidden; 
            mPlayer.Source = new Uri("https://youtu.be/" + viewModel.PlayingSong.Id);
            mPlayer.NavigationCompleted += delegate 
            {
                mPlayer.ExecuteScriptAsync("document.querySelector('.ytp-cued-thumbnail-overlay').click();");
                mPlayer.ExecuteScriptAsync("document.querySelector('.ytp-fullscreen-button.ytp-button').click();");
            };
            #endregion

            #region Timers
            DispatcherTimer timer;
            var tick = 0;
            timer = new DispatcherTimer() { Interval = TimeSpan.FromSeconds(1) };
            timer.Tick += delegate
            {
                tick++;
                if (tick > viewModel.PlayingSong.Duration)
                {
                    tick = 0;
                    viewModel.Play();
                    mPlayer.Source = new Uri("https://youtu.be/" + viewModel.PlayingSong.Id);
                }
            };
            timer.Start();

            // The timer of playlist
            timer = new() { Interval = TimeSpan.FromMilliseconds(1) };
            timer.Tick += delegate
            {
                mPlaylist.Margin = new(mPlaylist.Margin.Left - 2,
                    mPlaylist.Margin.Top, mPlaylist.Margin.Right, mPlaylist.Margin.Bottom);
                if (mPlaylist.Margin.Left < -6000)
                {
                    mPlaylist.Margin = new(ActualWidth,
                        mPlaylist.Margin.Top, mPlaylist.Margin.Right, mPlaylist.Margin.Bottom);
                } 
            };
            timer.Start();
            #endregion

            // TODO: Delegate the buttons
            //mControlPanel.Click += delegate { new HorizontalControl().ShowDialog(); };
        };
    }
}