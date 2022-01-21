using System.Text.Json;

namespace Music.Client.Winform;

internal static class Program
{
    private static readonly HttpClient client = new();
    private static readonly WebBrowser browser = new();

    [STAThread]
    static void Main()
    {
        ApplicationConfiguration.Initialize();
        Application.Run(new MainForm());
        //var itemId = "7030362436586753316";
        //var res = httpClient.GetStringAsync(
        //    $"https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids={ itemId }").Result;
        //var obj = JsonSerializer.Deserialize<JsonElement>(res);
        //var videoId = obj.GetProperty("item_list").EnumerateArray().ToArray()[0]
        //    .GetProperty("video").GetProperty("vid").GetString();
        //var url = "https://www.douyin.com/aweme/v1/play/?video_id=" + videoId;
        //WebBrowser browser = new();
        //browser.Navigate(url);

        //var u = "https://www.douyin.com/user/MS4wLjABAAAACdAvVQddEJGiWzfvNLrc5QseQmcmf6d2S3YOVSZIZKg";
        //var str = httpClient.GetStringAsync(u).Result;
        //var videos = Regex.Matches(str, @"<li class=""_4P45SWS7(.*?)</li>", RegexOptions.Singleline);
        //var ids = new List<string>();
        //foreach (var video in videos)
        //{
        //    var videoUri = Regex.Match(video.ToString(), @"www.douyin.com/video/(.*?)""", RegexOptions.Singleline).Value;
        //    ids.Add(videoUri.Substring(videoUri.LastIndexOf('/') + 1, 19));
        //}    
        //label1.Text = str;  
    }

    static void DownloadVideo(string id)
    {
        var responseBody = client.GetStringAsync(
            $"https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids={ id }").Result;
        var jsonElement = JsonSerializer.Deserialize<JsonElement>(responseBody);
        var videoId = jsonElement.GetProperty("item_list").EnumerateArray().ToArray()[0]
            .GetProperty("video").GetProperty("vid").GetString();
        var url = "https://www.douyin.com/aweme/v1/play/?video_id=" + videoId;
        browser.Navigate(url);
    }
}