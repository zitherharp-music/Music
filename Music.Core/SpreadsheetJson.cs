namespace Music.Core.Models;

public class SpreadsheetJson
{
    public IDictionary<string, string> Id { get; init; } 

    public IDictionary<string, string> Range { get; init; }

    public IList<string> Key { get; init; }

    public IDictionary<string, IDictionary<string, int>> Column { get; init; }
}