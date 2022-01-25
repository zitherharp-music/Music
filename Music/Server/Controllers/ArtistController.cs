using Microsoft.AspNetCore.Mvc;
using Music.Shared.Models;
using static Music.Server.Datas.Spreadsheet;
using static Music.Shared.Cores.Spreadsheet;

namespace Music.Server.Controllers;

[ApiController]
[Route("apis/[controller]")]
public class ArtistController : ControllerBase
{
    [HttpGet]
    public async Task<IList<Artist>> GetArtists()
    {
        var responseBody = await Service.Spreadsheets.Values
            .Get(MusicSpreadsheet.Id, MusicSpreadsheet.Range[nameof(Artist).ToLower()]).ExecuteAsync();
        return Serializer.Deserialize<IList<Artist>>(responseBody.Values);
    }
}