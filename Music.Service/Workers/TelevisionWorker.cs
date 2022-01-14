using Music.Core.Models;
using System.Text;
using System.Text.Json;

namespace Music.Core.Service.Workers;

internal class TelevisionWorker : BackgroundService
{
    private readonly ILogger<TelevisionWorker> logger;

    private Song playingSong;
    private IList<Song> playlist;

    public TelevisionWorker(ILogger<TelevisionWorker> logger)
    {
        this.logger = logger;
    }

    private void Fill()
    {
        if (playlist is null) throw new NullReferenceException(nameof(playlist));

        Song song;
        while (playlist.Count < 15)
        {
            song = Songs[Random.Shared.Next(Songs.Count)];
            if (playlist.Contains(song)) continue;
            playlist.Add(song);
        }
    }

    private void Play()
    {
        playingSong = playlist[0];
        playlist[0].Users.Clear();
        playlist.RemoveAt(0);
        Fill();
    }

    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        OnLoad(stoppingToken);
        playingSong = Songs[Random.Shared.Next(Songs.Count)];
        playlist = Songs.OrderBy(s => Random.Shared.Next()).Take(15).ToList();

        string jsonString;
        HttpResponseMessage responseMessage;
        while (!stoppingToken.IsCancellationRequested)
        {
            logger.LogInformation("Playing song: {content}", playingSong.VietnameseName);

            jsonString = JsonSerializer.Serialize(playingSong, Spreadsheet.JsonOptions);
            responseMessage = await Spreadsheet.HttpClient.PutAsync(
                "https://localhost:7149/television/playingsong/update",
                new StringContent(jsonString, Encoding.UTF8, "application/json"), stoppingToken);
            logger.LogInformation("Update the playing song: {j}", responseMessage.StatusCode);

            await Task.Delay(playingSong.Duration * 100, stoppingToken);
            Play();
        }
    }
}