using Music.Television.Wpf.ViewModels;
using Music.Television.Wpf.Views.Horizontal;
using System;
using System.Windows;
using System.Windows.Threading;

namespace Music.Television.Wpf.Views.Main;

public partial class MainWindow : Window
{
    private bool isFirstLoad = true;

    public MainWindow()
    {
        InitializeComponent();
        mMainContainer.Children.Clear();
        mMainContainer.Children.Add(new HorizontalScreen());
        mMainContainer.DataContextChanged += delegate
        {
            if (!isFirstLoad) return;
            var viewModel = mMainContainer.DataContext as BaseViewModel;
            mPlayerView.Navigate("https://youtu.be/" + viewModel?.PlayingSong.Id);

            // TODO: Initialize the timer
            var tick = 0;
            var timer = new DispatcherTimer() { Interval = TimeSpan.FromSeconds(1) };
            timer.Tick += delegate
            {
                tick++;
                if (tick > viewModel?.PlayingSong.Duration)
                {
                    tick = 0;
                    viewModel.Play();
                    mPlayerView.Navigate("https://youtu.be/" + viewModel.PlayingSong.Id);
                }
            };
            timer.Start();

            isFirstLoad = false;
        };    
    }
}