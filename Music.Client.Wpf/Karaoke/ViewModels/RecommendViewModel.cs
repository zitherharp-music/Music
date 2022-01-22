using Music.Shared.Cores;
using Music.Shared.Models;
using System.Collections.Generic;
using System.Linq;

namespace Music.Client.Wpf.Karaoke.ViewModels;

internal class RecommendViewModel : BaseViewModel
{
    public IList<Song> Songs { get; set; }

    private static RecommendViewModel? instance;
    public static RecommendViewModel Instance => instance ??= new();

    private RecommendViewModel()
    {
        Songs = Spreadsheet.Repository.Songs.Take(15).ToList();
    }
}