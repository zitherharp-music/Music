using Music.Models;
using System.Text;
using System.Text.Json;

namespace Music.Cores.Service.Workers;

internal class MusicWorker : BackgroundService
{
    private readonly ILogger<MusicWorker> logger;
    private string? jsonString;

    public IList<Song>? Songs { get; set; }
    public IList<Artist>? Artists { get; set; }

    public MusicWorker(ILoggerFactory factory)
    {
        logger = new Logger<MusicWorker>(factory);
    }

    protected override async Task ExecuteAsync(CancellationToken stoppingToken)
    {
        logger.LogInformation("Loading data from server...");
        while (!stoppingToken.IsCancellationRequested)
        {
            try
            {
                // TODO: Load songs
                jsonString = await Spreadsheet.HttpClient.GetStringAsync(
                    "https://localhost:7149/music/songs/get", stoppingToken);
                Songs = JsonSerializer.Deserialize<IList<Song>>(
                    new MemoryStream(Encoding.UTF8.GetBytes(jsonString)), Spreadsheet.JsonOptions);
                // TODO: Load artists
                jsonString = await Spreadsheet.HttpClient.GetStringAsync(
                    "https://localhost:7149/music/artists/get", stoppingToken);
                Artists = JsonSerializer.Deserialize<IList<Artist>>(
                    new MemoryStream(Encoding.UTF8.GetBytes(jsonString)), Spreadsheet.JsonOptions);
                logger.LogInformation("Done...");
            }
            catch (Exception e)
            {
                logger.LogError("{message} {traces}", e.Message, e.StackTrace);
            } 
            await Task.Delay(60000, stoppingToken);
            logger.LogInformation("Refreshing data from server...");
        }
    }
}