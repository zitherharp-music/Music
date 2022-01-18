using Music.Television.Wpf.ViewModels;
using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Threading;

namespace Music.Television.Wpf.Views.Vertical;

public partial class VerticalScreen : UserControl
{
    public VerticalScreen()
    {
        InitializeComponent();
        IsVisibleChanged += delegate
        {
            // TODO: Initialize the data
            DataContext = Window.GetWindow(this).DataContext;
            var viewModel = DataContext as VerticalViewModel;

            // TODO: Initialize the timers
            DispatcherTimer timer;

            // The timer of vote syntax
            //timer = new() { Interval = TimeSpan.FromSeconds(2) };
            //timer.Tick += delegate
            //{
            //    mVoteSyntax.Content = viewModel?.VoteSyntax;
            //};
            //timer.Start();

            // The timer of information and playlist
            timer = new() { Interval = TimeSpan.FromMilliseconds(1) };
            timer.Tick += delegate
            {
                mPlaylist.Margin = new(mPlaylist.Margin.Left - 2,
                    mPlaylist.Margin.Top, mPlaylist.Margin.Right, mPlaylist.Margin.Bottom);
                mInformation.Margin = new(mInformation.Margin.Left - 2,
                    mInformation.Margin.Top, mInformation.Margin.Right, mInformation.Margin.Bottom);
                if (mPlaylist.Margin.Left < -6000)
                {
                    mPlaylist.Margin = new(900,
                        mPlaylist.Margin.Top, mPlaylist.Margin.Right, mPlaylist.Margin.Bottom);
                }
                if (mInformation.Margin.Left < -4000)
                {
                    mInformation.Margin = new(900,
                        mInformation.Margin.Top, mInformation.Margin.Right, mInformation.Margin.Bottom);
                }
            };
            timer.Start();

            // TODO: Delegate the buttons
            mVoteSyntax.Click += delegate { };
        };
    }
}