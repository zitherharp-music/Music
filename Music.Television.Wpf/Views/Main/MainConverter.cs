using Library.Enums;
using Library.Providers;
using Library.Utils;
using System;
using System.Globalization;
using System.Windows.Data;

namespace Wpf.Views.Main;

internal class SongConverter : IValueConverter
{
    public object? Convert(object value, Type targetType, object parameter, CultureInfo culture)
    {
        var song = Provider.Songs.Find(song => song.Id != null && song.Id.Equals(value));
        return song != null ? song.ToString(Language.Vietnamese) : "(không xác định)";
    }

    public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
    {
        throw new NotImplementedException();
    }
}

internal class SongNameConverter : IValueConverter
{
    public object? Convert(object value, Type targetType, object parameter, CultureInfo culture)
    {
        var song = Provider.Songs.Find(song => song.Id != null && song.Id.Equals(value));
        return song != null ? song.GetName(Language.Vietnamese) : "(không xác định)";
    }

    public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
    {
        throw new NotImplementedException();
    }
}

internal class SongArtistConverter : IValueConverter
{
    public object? Convert(object value, Type targetType, object parameter, CultureInfo culture)
    {
        var song = Provider.Songs.Find(song => song.Id != null && song.Id.Equals(value));
        return song != null ? song.Artists.GetNames(Language.Vietnamese, "/") : "(không xác định)";
    }

    public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
    {
        throw new NotImplementedException();
    }
}