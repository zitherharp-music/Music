using ZitherHarp.Music.Shared.Cores;
using static ZitherHarp.Music.Shared.Cores.Spreadsheet.Property;
using static ZitherHarp.Music.Shared.Cores.Spreadsheet.Repository;

namespace ZitherHarp.Music.Shared.Models;

public partial class Artist : QQMusic
{
    private static readonly Lazy<IDictionary<string, Artist>> repository = new(() =>
    {
        Artist artist;
        var artists = new Dictionary<string, Artist>();
        foreach (var value in Read(nameof(Artist)))
        {
            artist = new()
            {
                Id = GetString(value, PrimaryId),
                chineseName = GetString(value, ChineseName),
                vietnameseName = GetString(value, VietnameseName)
            };
            artists.TryAdd(artist.Id, artist);
        }
        return artists;
    });

    public static new IDictionary<string, Artist> Repository => repository.Value;
}
