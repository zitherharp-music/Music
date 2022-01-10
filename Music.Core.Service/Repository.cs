using Music.Core.Models.Music;
using System.Text;
using System.Text.Json;

namespace Music.Core.Service;

internal class Repository
{
    public static readonly HttpClient HttpClient = new();
    public static readonly JsonSerializerOptions JsonOptions = new()
    {
        PropertyNameCaseInsensitive = true
    };

    public static IList<Song>? Songs { get; set; } 
    public static IList<Artist>? Artists { get; set; }

    public static async void OnLoad(CancellationToken cancellationToken)
    {
        string? jsonString;
        // TODO: Load songs
        jsonString = await HttpClient.GetStringAsync(
            "https://localhost:7149/music/songs/get", cancellationToken);
        Songs = JsonSerializer.Deserialize<IList<Song>>(
            new MemoryStream(Encoding.UTF8.GetBytes(jsonString)), JsonOptions);
        // TODO: Load artists
        jsonString = await HttpClient.GetStringAsync(
            "https://localhost:7149/music/artists/get", cancellationToken);
        Artists = JsonSerializer.Deserialize<IList<Artist>>(
            new MemoryStream(Encoding.UTF8.GetBytes(jsonString)), JsonOptions);
    }
}