using Music.Television.Wpf.ViewModels;
using System;
using System.Windows;
using System.Windows.Threading;

namespace Music.Television.Wpf.Views.Main;

public partial class MainWindow : Window
{
    public MainWindow()
    {
        InitializeComponent();
        DataContextChanged += delegate
        {
            var viewModel = DataContext as BaseViewModel;
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
        };
    }
}