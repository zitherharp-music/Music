using System.Text.Json;

namespace Music.Client.Winform
{
    public partial class MainForm : Form
    {
        private readonly HttpClient client = new();

        public MainForm()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            var itemId = textBox1.Text;
            var res = client.GetStringAsync(
                $"https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids={ itemId }").Result;
            var obj = JsonSerializer.Deserialize<JsonElement>(res);
            var videoId = obj.GetProperty("item_list").EnumerateArray().ToArray()[0]
                .GetProperty("video").GetProperty("vid").GetString();
            var url = "https://www.douyin.com/aweme/v1/play/?video_id=" + videoId;
            WebBrowser browser = new();
            browser.Navigate(url);
            textBox1.ResetText();
        }
    }
}
