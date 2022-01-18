using Music.Server.Service.Workers;

var host = Host.CreateDefaultBuilder(args)
    .ConfigureServices(services =>
    {
        services.AddHostedService<TelevisionWorker>();
    })
    .Build();

await host.RunAsync();