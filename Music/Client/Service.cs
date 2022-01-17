using Microsoft.AspNetCore.Components.WebAssembly.Hosting;
using Music.Shared.Models;
using System.Net.Http.Json;

namespace Music.Client;

internal class Service : IService
{
    private readonly IList<Song>? songs;
    private readonly IList<Artist>? artists;
    HttpClient httpClient;

    public Service()
    {
        httpClient = new HttpClient { BaseAddress = new Uri(WebAssemblyHostBuilder.CreateDefault().HostEnvironment.BaseAddress) };
    }

    public async Task<IList<Song>> GetSongs()
    {
        return await httpClient.GetFromJsonAsync<IList<Song>>("apis/music/songs/get");
    }

    public async Task<IList<Artist>> GetArtists() => await httpClient.GetFromJsonAsync<IList<Artist>>("apis/music/artists/get");
}

internal interface IService
{
    Task<IList<Song>> GetSongs();

    Task<IList<Artist>> GetArtists();
}    