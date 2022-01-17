using Microsoft.AspNetCore.Components.Web;
using Microsoft.AspNetCore.Components.WebAssembly.Hosting;
using Music.Client;
using Music.Shared.Models;
using System.Net.Http.Json;

var builder = WebAssemblyHostBuilder.CreateDefault(args);
builder.RootComponents.Add<App>("#app");
builder.RootComponents.Add<HeadOutlet>("head::after");

var httpClient = new HttpClient { BaseAddress = new Uri(builder.HostEnvironment.BaseAddress) };
builder.Services.AddScoped(sp => httpClient); 

await builder.Build().RunAsync();
