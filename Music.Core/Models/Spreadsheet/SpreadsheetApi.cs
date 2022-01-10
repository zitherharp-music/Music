namespace Music.Core.Models.Spreadsheet;

public class SpreadsheetApi
{
    public string? Range { get; init; }

    public string? MajorDimension { get; init; }

    public IList<IList<object>>? Values { get; init; }
}