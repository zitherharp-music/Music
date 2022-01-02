using System.Globalization;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Media;

namespace Wpf;

internal static class Util
{
    public static Size GetSize(this TextBlock textBlock, string? text)
    {  
        var formattedText = new FormattedText(text, CultureInfo.CurrentCulture, FlowDirection.LeftToRight,
            new Typeface(textBlock.FontFamily, textBlock.FontStyle, textBlock.FontWeight, textBlock.FontStretch),
            textBlock.FontSize, Brushes.Black, new NumberSubstitution(), 1);
        return new Size(formattedText.Width, formattedText.Height);
    }
}