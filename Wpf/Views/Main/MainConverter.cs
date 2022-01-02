using Library.Enums;
using Library.Models;
using Library.Utils;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Windows.Data;

namespace Wpf.Views.Main;

internal class PlaylistConverter : IValueConverter
{
    public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
    {
        var playlist = (List<Song>)value;
        var playlistString = string.Empty;
        for (int i = 0; i < 15; i++)
        {
            playlistString += playlist[i].ToString(Language.Vietnamese) + "; ";
        }
        return playlistString;
    }

    public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
    {
        throw new NotImplementedException();
    }
}

internal class SongVoteConverter : IValueConverter
{
    public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
    {
        return ((Song)value).Users.Count * 10 + 10;
    }

    public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
    {
        throw new NotImplementedException();
    }
}

internal class SongImageConverter : IValueConverter
{
    public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
    {
        return ((Song)value).GetPhotoUrl(PhotoQuality.MaxResDefault);
    }

    public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
    {
        throw new NotImplementedException();
    }
}

internal class SongDetailConverter : IValueConverter
{
    public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
    {
        var song = (Song)value;
        var detail = "Đang phát: " + song.GetName(Language.Vietnamese)
            + "\nThể hiện: " + song.Artists.GetNames(Language.Vietnamese, " & ");
        if (song.Users.Count > 0)
        {
            detail = "Khán giả yêu cầu nhanh nhất: " + song.Users[0].Name + "\n" + detail;
        }    
        return detail;
    }

    public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
    {
        throw new NotImplementedException();
    }
}