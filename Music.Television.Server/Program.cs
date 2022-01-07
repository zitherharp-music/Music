using Library.Enums;
using Library.Models;
using Newtonsoft.Json;
using System.Text;

const string applicationName = "Zither Harp Music: Television";
const string id = "id";
const string artistId = "artistId";
const string vietnameseName = "vietnameseName";
const string simplifiedChineseName = "simplifiedChineseName";
const string traditionalChineseName = "traditionalChineseName";
const string pinyinName = "pinyinName";
const string duration = "duration";

Console.Title = applicationName;
Console.OutputEncoding = Encoding.Unicode;

void SetTitle(string title) => Console.Title = $"{ applicationName } - { title }";

var client = new HttpClient();
SetTitle("Loading songs from server...");
var songJson = await client.GetStringAsync("https://localhost:7149/music/songs");
SetTitle("Loading artists from server...");
var artistJson = await client.GetStringAsync("https://localhost:7149/music/artists");

SetTitle("Syncing data from server...");
dynamic? songArray = JsonConvert.DeserializeObject(songJson);
dynamic? artistArray = JsonConvert.DeserializeObject(artistJson);

if (songArray is null) throw new InvalidCastException(nameof(songJson));
if (artistArray is null) throw new InvalidCastException(nameof(artistJson));

string GetString(object obj) => obj.ToString() ?? string.Empty;
int GetInteger(object obj) => int.TryParse(GetString(obj), out int i) ? i : 0;

var songs = new List<Song>();
var artists = new List<Artist>();

SetTitle("Loading songs to client...");
foreach (var values in songArray)
{
    if (values.Count == 0) continue;
    var song = new Song(GetString(values[id]), 
        GetString(values[artistId]), GetInteger(values[duration]));
    song.SetName(Language.Vietnamese, GetString(values[vietnameseName]));
    song.SetName(Language.SimplifiedChinese, GetString(values[simplifiedChineseName]));
    song.SetName(Language.TraditionalChinese, GetString(values[traditionalChineseName]));
    song.SetName(Language.Pinyin, GetString(values[pinyinName]));
    songs.Add(song);
}

SetTitle("Loading artists to client...");
foreach (var values in artistArray)
{
    if (values.Count == 0) continue;
    var artist = new Artist(GetString(values[id]));
    artist.SetName(Language.Vietnamese, GetString(values[vietnameseName]));
    artist.SetName(Language.SimplifiedChinese, GetString(values[simplifiedChineseName]));
    artist.SetName(Language.TraditionalChinese, GetString(values[traditionalChineseName]));
    artist.SetName(Language.Pinyin, GetString(values[pinyinName]));
    artists.Add(artist);
}

SetTitle("Preparing to play...");
var language = Language.Vietnamese;
var playingSong = songs[Random.Shared.Next(songs.Count)];
var playlist = songs.OrderBy(s => Random.Shared.Next()).Take(15).ToList();

void Fill()
{
    if (songs is null) throw new NullReferenceException(nameof(songs));
    if (playlist is null) throw new NullReferenceException(nameof(playlist));

    Song song;
    while (playlist.Count < 15)
    {
        song = songs[Random.Shared.Next(songs.Count)];
        if (playlist.Contains(song)) continue;
        playlist.Add(song);
    }
}

void Play()
{
    if (playlist is null) throw new NullReferenceException(nameof(playlist));

    playingSong = playlist[0];
    playlist[0].Users.Clear();
    playlist.RemoveAt(0);
    Fill();
    SetTitle("Playing: " + playingSong.ToString(language));
}

var tick = 0;
var timer = new Timer(TimerCallback, null, 0, 1000);

void TimerCallback(object? obj)
{
    tick++;
    if (tick > playingSong.Duration)
    {
        tick = 0;
        Play();
    }
}

while (true)
{
    switch (Console.ReadLine())
    {
        case "playlist":
            Console.WriteLine($"Found { playlist.Count } results:");
            playlist.ForEach(song => Console.WriteLine(song.ToString(language)));
            break;
        case "songs":
            Console.WriteLine($"Found { songs.Count } results:");
            songs.ForEach(song => Console.WriteLine(song.ToString(language)));
            break;
        case "artists":
            Console.WriteLine($"Found { artists.Count } results:");
            artists.ForEach(artist => Console.WriteLine(artist.GetName(language)));
            break;
        case "clear":
            Console.Clear();
            break;
        case "stop":
            Environment.Exit(0);
            break;
    }
}