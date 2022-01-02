using Library.Enums;
using Library.Models;
using System;
using System.Globalization;
using System.Windows.Data;

namespace Wpf.Views.Horizontal;

internal class SongNameConverter : IValueConverter
{
    public object? Convert(object value, Type targetType, object parameter, CultureInfo culture)
    {
        return ((Song)value).GetName(Language.Vietnamese);
    }

    public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
    {
        throw new NotImplementedException();
    }
}