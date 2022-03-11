namespace ZitherHarp.Music.Shared.Cores;

public abstract partial class Spreadsheet
{
    public string? Id { get; init; }

    protected string? chineseName, vietnameseName;
    protected string? chineseDescription, vietnameseDescription;

    public string? GetName(Language language)
    {
        return language switch
        {
            Language.Chinese => chineseName,
            Language.Vietnamese => vietnameseName,
            _ => null
        };
    }

    public string? GetDescription(Language language)
    {
        return language switch
        {
            Language.Chinese => chineseDescription,
            Language.Vietnamese => vietnameseDescription,
            _ => null
        };
    }
}