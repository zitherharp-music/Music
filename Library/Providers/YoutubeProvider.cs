using Google.Apis.Auth.OAuth2;
using Google.Apis.Services;
using Google.Apis.Util.Store;
using Google.Apis.YouTube.v3;
using Library.Models;

namespace Library.Providers;

public partial class Provider
{
    private static YouTubeService? youtubeService;
    private static string? liveChatId;

    private static readonly List<Message> messages = new();

    public static bool IsServiceActived => youtubeService != null;

    public async static Task InitializeService(string liveStreamingId)
    {
        if (youtubeService == null)
        {
            var instance = new Provider();
            using var stream = new FileStream("client_secrets.json", FileMode.Open, FileAccess.Read);
            var credential = await GoogleWebAuthorizationBroker.AuthorizeAsync(
                GoogleClientSecrets.FromStream(stream).Secrets, new[] { YouTubeService.Scope.Youtube },
                "user", CancellationToken.None, new FileDataStore(instance.GetType().ToString()));
            youtubeService = new YouTubeService(new BaseClientService.Initializer()
            {
                HttpClientInitializer = credential,
                ApplicationName = instance.GetType().ToString()
            });
        }    
        var videoListRequest = youtubeService.Videos.List("liveStreamingDetails");
        if (videoListRequest is not null)
        { 
            videoListRequest.Id = liveStreamingId;
            var videoListResponse = videoListRequest.Execute();
            liveChatId = videoListResponse.Items[0].LiveStreamingDetails.ActiveLiveChatId;
        }
    }

    public static List<Message> Messages
    {
        get
        {
            try
            {
                var messageList = youtubeService?.LiveChatMessages
                    .List(liveChatId, "snippet,authorDetails").Execute();
                if (messageList is not null)
                {
                    foreach (var item in messageList.Items)
                    {
                        if (messages.Find(message => item.Id.Equals(message.Id)) == null)
                        {
                            var user = new User()
                            {
                                Id = item.AuthorDetails.ChannelId,
                                Name = item.AuthorDetails.DisplayName,
                            };
                            messages.Add(new Message()
                            {
                                User = user,
                                Id = item.Id,
                                Content = item.Snippet.DisplayMessage
                            });
                        }
                    }
                }
            } 
            catch (Exception e) 
            {
                Console.WriteLine(e.Message);
            }
            return messages;
        }
    }
}