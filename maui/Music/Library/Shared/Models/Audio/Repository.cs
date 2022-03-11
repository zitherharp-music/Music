using ZitherHarp.Music.Shared.Cores;
using static ZitherHarp.Music.Shared.Cores.Spreadsheet.Property;
using static ZitherHarp.Music.Shared.Cores.Spreadsheet.Repository;

namespace ZitherHarp.Music.Shared.Models;

public partial class Audio : Youtube
{
    new class Property : Spreadsheet.Property
    {
        public const int ArtistId = 1;
        public const int Duration = 4;
        public const int ChineseLyric = 7;
        public const int VietnameseLyric = 8;
    }

    private static readonly Lazy<IDictionary<string, Audio>> repository = new(() =>
    {
        Audio audio;
        var audios = new Dictionary<string, Audio>();
        foreach (var value in Read(nameof(Audio)))
        {
            audio = new()
            {
                Id = GetString(value, PrimaryId),
                Duration = GetInteger(value, Property.Duration),
                artistId = GetString(value, Property.ArtistId),
                chineseName = GetString(value, ChineseName),
                vietnameseName = GetString(value, VietnameseName)
            };
            audios.TryAdd(audio.Id, audio);
        }
        return audios;
    });

    public static new IDictionary<string, Audio> Repository => repository.Value;
}
