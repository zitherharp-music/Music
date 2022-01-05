namespace Music.WebApi;

internal static class Util
{
    public static string GetString(this object value)
    => value.ToString() ?? throw new NullReferenceException(nameof(value));

    public static int GetInteger(this object value) => Convert.ToInt32(value);
}