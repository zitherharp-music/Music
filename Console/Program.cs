await Service.Initialize();
Provider.Initialize();

var tick = 0;
var timer = new Timer(TimerCallback, null, 0, 1000);
Console.OutputEncoding = System.Text.Encoding.UTF8;
while (true)
{
    switch (Console.ReadLine())
    {
        case "exit":
            Environment.Exit(0);
            break;
        case "-get playingsong":
            Console.WriteLine(Provider.PlayingSong?.ToString(Provider.Language));
            break;
        case "-get playlist":
            foreach (var song in Provider.Playlist)
            {
                Console.WriteLine(song.ToString(Provider.Language)); 
            }
            break;
        case "-get songs":
            foreach (var song in Service.Songs)
            {
                Console.WriteLine(song.ToString(Provider.Language));
            }
            break;
        case "cls":
            Console.Clear();
            break;
    }
}

void TimerCallback(object? o)
{
    tick++;
    if (tick > Provider.PlayingSong?.Duration)
    {
        tick = 0;
        Provider.Play();
    }
}
