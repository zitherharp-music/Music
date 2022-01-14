using Music.Core.Service.Workers;

var host = Host.CreateDefaultBuilder(args)
    .ConfigureServices(services =>
    {
        //services.AddHostedService<MusicWorker>();
        services.AddHostedService<TelevisionWorker>();
    })
    .Build();

await host.RunAsync();