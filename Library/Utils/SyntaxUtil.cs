using Library.Enums;
using Library.Models;

namespace Library.Utils;

public static partial class Util
{
    private const string syntax = "ZHM ";
    private const string a = "áàảãạăắằẳẵặâấầẩẫậ";
    private const string e = "éèẻẽẹêếềểễệ";
    private const string i = "íìỉĩị";
    private const string o = "óòỏõọôốồổỗộơớờởỡợ";
    private const string u = "úùủũụưứừửữự";

    public static string ToUnsigned(this string s)
    {
        foreach (var c in s)
        {
            if ('đ'.Equals(c)) s = s.Replace(c, 'd');
            if (a.Contains(c)) s = s.Replace(c, 'a');
            if (e.Contains(c)) s = s.Replace(c, 'e');
            if (i.Contains(c)) s = s.Replace(c, 'i');
            if (o.Contains(c)) s = s.Replace(c, 'o');
            if (u.Contains(c)) s = s.Replace(c, 'u');

            if ('Đ'.Equals(c)) s = s.Replace(c, 'D');
            if (a.ToUpper().Contains(c)) s = s.Replace(c, 'A');
            if (e.ToUpper().Contains(c)) s = s.Replace(c, 'E');
            if (i.ToUpper().Contains(c)) s = s.Replace(c, 'I');
            if (o.ToUpper().Contains(c)) s = s.Replace(c, 'O');
            if (u.ToUpper().Contains(c)) s = s.Replace(c, 'U');
        }    
        return s;
    }

    public static Song? Find(this IList<Song> songs, Language language, string name)
    {
        foreach (var song in songs)
        {
            var songName = song.GetName(language);
            if (songName is not null)
            {
                if (songName.Equals(name)) return song;
            }    
        }
        return null;
    }
}