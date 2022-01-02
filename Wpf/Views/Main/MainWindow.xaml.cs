using Library.Providers;
using System;
using System.Windows;
using System.Windows.Threading;
using Wpf.ViewModels;

namespace Wpf.Views.Main;

public partial class MainWindow : Window
{
    public MainWindow()
    {
        InitializeComponent();
        var viewModel = DataContext as BaseViewModel;
        // TODO: Initialize the timer
        var tick = 0;
        var timer = new DispatcherTimer() { Interval = TimeSpan.FromSeconds(1) };
        if (mBasicScreen.IsVisible)
        {
            timer.Tick += delegate
            {
                tick++;
                if (tick > viewModel?.PlayingSong.Duration)
                {
                    tick = 0;
                    viewModel.Play();
                }
            };
        }    
        else
        {
            mPlayerView.Navigate("https://youtu.be/" + viewModel?.PlayingSong.Id);
            timer.Tick += delegate
            {
                tick++;
                if (tick % 30 == 0) if (Provider.IsServiceActived) viewModel?.Update();
                if (tick > viewModel?.PlayingSong.Duration)
                {
                    tick = 0;
                    viewModel.Play();
                    mPlayerView.Navigate("https://youtu.be/" + viewModel.PlayingSong.Id);
                }
            };
        }
        timer.Start();
    }
}