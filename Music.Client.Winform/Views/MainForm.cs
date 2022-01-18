using System.Net;
using System.Text.RegularExpressions;

namespace Music.Client.Winform.Views;

public partial class MainForm : Form
{
    public MainForm()
    {
        InitializeComponent();
        webView21.Source = new Uri("https://www.youtube.com/embed/wikdN8E4k48");
        webView21.NavigationCompleted += delegate { webView21.ExecuteScriptAsync("document.querySelector('.ytp-cued-thumbnail-overlay').click();"); };
    }
}