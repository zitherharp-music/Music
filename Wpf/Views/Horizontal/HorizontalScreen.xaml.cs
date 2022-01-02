using Library.Utils;
using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Threading;
using Wpf.ViewModels;

namespace Wpf.Views.Horizontal;

public partial class HorizontalScreen : UserControl
{
    public HorizontalScreen()
    {
        InitializeComponent();
        if (!IsVisible) return;

        // TODO: Initialize the data
        DataContext = Window.GetWindow(this).DataContext;
        var viewModel = DataContext as HorizontalViewModel;

        // TODO: Initialize the timers
        DispatcherTimer timer;

        // The timer of vote syntax
        timer = new() { Interval = TimeSpan.FromSeconds(2) };
        timer.Tick += delegate
        {
            mVoteSyntax.Text = viewModel?.Songs.GetVoteSyntax(viewModel.Language);
        };
        timer.Start();

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

        // TODO: Delegate the buttons
        mControlPanel.Click += delegate { new HorizontalControl().ShowDialog(); };
    }
}