using System.Text.Json;

namespace Music.Core.Models;

public class Spreadsheet
{
    public static readonly JsonSerializerOptions JsonOptions = new()
    {
        PropertyNameCaseInsensitive = true
    };

    private static Json? jsonValues;
    public static Json JsonValues
    {
        get
        {
            if (jsonValues is null)
            {
                using var stream = new StreamReader("spreadsheet.json");
                jsonValues = JsonSerializer.Deserialize<Spreadsheet.Json>(stream.ReadToEnd(), JsonOptions) ??
                    throw new NullReferenceException(nameof(JsonValues));
            }
            return jsonValues;
        }
    }

    public class Api
    {
        public string? Range { get; init; }

        public string? MajorDimension { get; init; }

        public IList<IList<object>>? Values { get; init; }
    }

    public class Json
    {
        public IDictionary<string, string> Id { get; init; }

        public IDictionary<string, string> Range { get; init; }

        public IDictionary<string, IDictionary<string, int>> Column { get; init; }

        public IList<string> Key { get; init; }
    }
}