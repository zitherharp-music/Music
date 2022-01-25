using Music.Shared;
using Music.Shared.Cores;
using System;
using System.Globalization;
using System.Linq;
using System.Windows.Data;

namespace Music.Client.Wpf.Karaoke.Converters;

internal class SongArtistConverter : IValueConverter
{
    public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
    {
        var song = Spreadsheet.Repository.Audios.ToList().Find(s => value.Equals(s.Id));
        if (song == null) return string.Empty;
        return song.GetArtists().GetNames(Shared.Enums.Language.Vietnamese);
    }

    public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
    {
        throw new NotImplementedException();
    }
}