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
        Loaded += delegate
        {
            var viewModel = DataContext as BaseViewModel;

            // TODO: Initialize the timer
            var tick = 0;
            var timer = new DispatcherTimer() { Interval = TimeSpan.FromSeconds(1) };
            //if (mMainScreen.IsVisible)
            //{
            //    timer.Tick += delegate
            //    {
            //        tick++; 
            //        if (tick > viewModel?.PlayingSong.Duration)
            //        {
            //            tick = 0;
            //            viewModel.Play();
            //        }
            //    };
            //}
            //else
            {
                //var options = new CoreWebView2EnvironmentOptions("--autoplay-policy=no-user-gesture-required");
                //var path = @"C:\Program Files (x86)\Microsoft\EdgeWebView\Application\96.0.1054.62";
                
                //mPlayerView.Loaded += async delegate
                //{
                //    await mPlayerView.EnsureCoreWebView2Async();
                //    mPlayerView.CoreWebView2.NavigateToString(@"<iframe width=""420"" height=""345"" src=""http://www.youtube.com/embed/oHg5SJYRHA0?autoplay=1"" frameborder=""0"" allow=""autoplay; encrypted-media"" allowfullscreen ></iframe>");
                //};
                timer.Tick += delegate
                {
                    tick++;
                    if (tick % 30 == 0) if (Provider.IsServiceActived) viewModel?.Update();
                    if (tick > viewModel?.PlayingSong.Duration)
                    {
                        tick = 0;
                        viewModel.Play();
                        //mPlayerView.NavigateToString("https://youtu.be/" + viewModel.PlayingSong.Id);
                    }
                };
            }
            timer.Start();
        };
    }
}