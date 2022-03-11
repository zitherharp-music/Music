using System.Net.NetworkInformation;
using System.Text.Json;

namespace ZitherHarp.Music.Shared.Cores;

public abstract partial class Spreadsheet
{
    public string Path => Repository.GetRootPath(GetType().Name);

    public sealed class Repository
    {
        sealed record Api(string Range, string MajorDimension, IList<IList<object>> Values);

        static readonly HttpClient httpClient = new();
        static readonly JsonSerializerOptions jsonOptions = new()
        {
            PropertyNameCaseInsensitive = true
        };

        static readonly string rootPath = GetRootPath(nameof(Spreadsheet));

        internal static string GetString(IList<object>? values, int index)
            => values is not null ? values[index].ToString() ?? string.Empty : string.Empty;

        internal static int GetInteger(IList<object> values, int index)
            => int.TryParse(GetString(values, index), out int i) ? i : 0;

        internal static IList<IList<object>> Read(string name)
            => JsonSerializer.Deserialize<Api>(File.ReadAllText(rootPath + name), jsonOptions)!.Values;

        public static void Write(params string[] names)
        {
            var id = "1znQOtTDJz0UqDs0uB2MQZV3wN0l_J0TrU44d9chH2SI";
            var key = "AIzaSyAD91OiEeWRoqhsw0peq94qg5joZe47r_s";
            var url = $"https://sheets.googleapis.com/v4/spreadsheets/{id}/values";
            foreach (var name in names)
            {
                var uri = $"{url}/{name}?key={key}";
                var json = httpClient.GetStringAsync(uri).Result;
                File.WriteAllTextAsync(rootPath + name, json).Wait();
            }
        }

        public static string GetRootPath(string name)
        {
            var path = $@"{FileSystem.AppDataDirectory}\{name}\";
            Directory.CreateDirectory(path);
            return path;
        }

        public static bool IsNetworkConnected()
        {
            try
            {
                return new Ping().Send("google.com", 5000, 
                    new byte[32], new()).Status == IPStatus.Success;
            }
            catch (Exception) { return false; }
        }
    }
}