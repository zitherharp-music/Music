using Music.Client.Wpf.Television.ViewModels;
using System;
using System.Windows.Controls;
using System.Windows.Threading;

namespace Music.Client.Wpf.Television.Views.Horizontal;

public partial class ScreenPage : Page
{
    public ScreenPage(HorizontalViewModel viewModel)
    {
        InitializeComponent();
        Loaded += delegate
        {
            DispatcherTimer timer;
            // The timer of vote syntax
            timer = new() { Interval = TimeSpan.FromSeconds(2) };
            timer.Tick += delegate
            {
                mVoteSyntax.Text = viewModel.VoteSyntax;
            };
            timer.Start();

            // The timer of information and playlist
            timer = new() { Interval = TimeSpan.FromMilliseconds(1) };
            timer.Tick += delegate
            {
                mInformation.Margin = new(mInformation.Margin.Left - 2,
                mInformation.Margin.Top, mInformation.Margin.Right, mInformation.Margin.Bottom);
                if (mInformation.Margin.Left < -4000)
                {
                    mInformation.Margin = new(ActualWidth - mVoteSyntax.RenderSize.Width,
                        mInformation.Margin.Top, mInformation.Margin.Right, mInformation.Margin.Bottom);
                }
            };
            timer.Start();
        };
    }
}