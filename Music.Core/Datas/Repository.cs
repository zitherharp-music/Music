using Music.Core.Models;
using static Music.Core.Service;

namespace Music.Core.Datas;

public class Repository
{
    private static List<Song>? songs;
    public static List<Song> Songs
    {
        get
        {
            if (songs == null)
            {
                songs = new(); 
                var range = SheetsService.Spreadsheets.Values
                    .Get(Spreadsheet.Id["Music"], Spreadsheet.Range["Audio"]).Execute();
                foreach (var row in range.Values)
                {
                    var song = new Song()
                    {
                        Id = row[Spreadsheet.Column["Id"]].ToString(),
                        ArtistId = row[Spreadsheet.Column["ArtistId"]].ToString(),
                        VietnameseName = row[Spreadsheet.Column["VietnameseName"]].ToString(),
                        SimplifiedChineseName = row[Spreadsheet.Column["SimplifiedChineseName"]].ToString(),
                        TraditionalChineseName = row[Spreadsheet.Column["TraditionalChineseName"]].ToString(),
                        PinyinName = row[Spreadsheet.Column["Id"]].ToString(),
                        Duration = Convert.ToInt32(row[Spreadsheet.Column["Duration"]]),
                    };
                    Songs.Add(song);
                }
            }
            return songs;
        }
    }
}