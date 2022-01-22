using System.ComponentModel;
using System.Runtime.CompilerServices;

namespace Music.Client.Wpf.Karaoke.ViewModels;

internal class BaseViewModel : INotifyPropertyChanged
{
    public event PropertyChangedEventHandler? PropertyChanged;
    protected void OnPropertyChanged([CallerMemberName] string? propertyName = null)
        => PropertyChanged?.Invoke(this, new(propertyName));
}