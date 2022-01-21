using System;
using System.Linq;
using System.Net.Http;
using System.Text.Json;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;

namespace Music.Client.Helper.Short;

public partial class MainWindow : Window
{
    private readonly HttpClient client = new();
    private readonly WebBrowser browser = new();

    public MainWindow()
    {
        InitializeComponent();
        mDownloadVideo.Click += delegate
        {
            try
            {
                var components = mAddress.Text.Split('/');
                var itemId = components[^1];
                var responseBody = client.GetStringAsync(
                $"https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids={ itemId }").Result;
                var jsonElement = JsonSerializer.Deserialize<JsonElement>(responseBody);
                var videoId = jsonElement.GetProperty("item_list").EnumerateArray().ToArray()[0]
                    .GetProperty("video").GetProperty("vid").GetString();
                var url = "https://www.douyin.com/aweme/v1/play/?video_id=" + videoId;
                browser.Navigate(url);
                mAddress.Text = string.Empty;
                BringIntoView();
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        };
        mConvertTitle.Click += delegate
        {
            var text = new TextRange(mSource.Document.ContentStart, mSource.Document.ContentEnd).Text;
            var components = text.Split("\n");
            mTarget.Text = text.Split("\n")[1] + "\n\r" + text.Split("\n")[3];
        };
    }
}