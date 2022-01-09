using System.Text.Json;

namespace Music.Core.Extensions;

public static partial class Extension
{
    public static JsonElement GetProperty(this JsonElement jsonElement, params string[] keys)
    {
        foreach (var key in keys)
        {
            jsonElement = jsonElement.GetProperty(key);
        }
        return jsonElement;
    }

    public static string? GetString(this JsonElement jsonElement, params string[] keys)
    {
        foreach (var key in keys)
        {
            jsonElement = jsonElement.GetProperty(key);
        }    
        return jsonElement.GetString();
    }

    public static int GetInt32(this JsonElement jsonElement, params string[] keys)
    {
        foreach (var key in keys)
        {
            jsonElement = jsonElement.GetProperty(key);
        }
        return jsonElement.GetInt32();
    }
}