using Music.Models;
using static Music.Cores.Spreadsheet;

namespace Music.Short.Blazor.Data;

public class MusicService
{
    public Task<IList<Artist>> GetArtistsAsync()
    {
        return Task.FromResult(Repository.Artists);
    }

    public Task<IList<Song>> GetSongsAsync()
    {
        return Task.FromResult(Repository.Songs);
    }

    public Task<IList<Models.Short>> GetShortsAsync()
    {
        return Task.FromResult(Repository.Shorts);
    }
}