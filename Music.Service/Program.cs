using Music.Cores.Service.Workers;

var host = Host.CreateDefaultBuilder(args)
    .ConfigureServices(services =>
    {
        services.AddHostedService<TelevisionWorker>();
    })
    .Build();

await host.RunAsync();