using Microsoft.AspNetCore.Components.WebAssembly.Hosting;
using Music.Shared.Models;
using System.Net.Http.Json;

namespace Music.Client;

internal class Service 
{
    private IList<Song>? songs;

    public IList<Song> Songs
    {
        get
        {
            if (songs is null)
            {
                songs = new List<Song>();
            }
            return songs;
        }
    }
}   