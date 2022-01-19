using Music.Shared;

namespace Music.Client.Wpf.Television.ViewModels;

public class HorizontalViewModel : HoriverticalViewModel
{
    private static HorizontalViewModel? instance;
    public static HorizontalViewModel Instance => instance ??= new();

    public string? VoteSyntax => "Soạn tin: ZHM " + songs.GetRandom().GetName(Language);
}