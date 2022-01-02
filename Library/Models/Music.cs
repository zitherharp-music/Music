using Library.Enums;

namespace Library.Models;

public class Music
{
    protected const string splitCharacter = "/";

    protected string? id;
    protected string? vietnameseName, simplifiedChineseName, traditionalChineseName, pinyinName;
    protected string? vietnameseDescription, simplifiedChineseDescription, traditionalChineseDescription;

    public string? Id => id;

    public Music SetName(Language language, string name)
    {
        switch (language)
        {
            case Language.Vietnamese: vietnameseName = name; break;
            case Language.SimplifiedChinese: simplifiedChineseName = name; break;
            case Language.TraditionalChinese: traditionalChineseName = name; break;
            case Language.Pinyin: pinyinName = name; break;
        }    
        return this;
    }

    public Music SetDescription(Language language, string description)
    {
        switch (language)
        {
            case Language.Vietnamese: vietnameseDescription = description; break;
            case Language.SimplifiedChinese: simplifiedChineseDescription = description; break;
            case Language.TraditionalChinese: traditionalChineseDescription = description; break;
        }
        return this;
    }

    public string? GetName(Language language)
    {
        return language switch
        {
            Language.SimplifiedChinese => simplifiedChineseName,
            Language.TraditionalChinese => traditionalChineseName,
            Language.Pinyin => pinyinName,
            Language.Vietnamese => vietnameseName,
            _ => throw new NotImplementedException(),
        };
    }

    public string? GetDescription(Language language)
    {
        return language switch
        {
            Language.SimplifiedChinese => simplifiedChineseDescription,
            Language.TraditionalChinese => traditionalChineseDescription,
            _ => vietnameseDescription
        }; 
    }

    public string GetPhotoUrl(PhotoQuality photoQuality)
    {
        return photoQuality switch
        {
            PhotoQuality.Small or PhotoQuality.Medium or PhotoQuality.Large
                => $"https://y.qq.com/music/photo_new/T001R{ (int)photoQuality }x{ (int)photoQuality }M000{ id }.jpg",
            _ => $"https://i.ytimg.com/vi/{ id }/{ photoQuality.ToString().ToLower() }.jpg"
        };
    }
}