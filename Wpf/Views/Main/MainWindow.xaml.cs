using Library.Providers;
using System;
using System.Windows;
using System.Windows.Threading;
using Wpf.ViewModels;

namespace Wpf.Views.Main;

public partial class MainWindow : Window
{
    private MainViewModel? viewModel;

    public MainWindow()
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
        viewModel = DataContext as MainViewModel;
        mPlayerView.Navigate("https://youtu.be/" + viewModel?.PlayingSong.Id);
    }

    private void InitializeTimer()
    {
        var tick = 0;
        var timer = new DispatcherTimer() { Interval = TimeSpan.FromSeconds(1) };
        timer.Tick += (s, e) =>
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
        timer.Start();
    }
}