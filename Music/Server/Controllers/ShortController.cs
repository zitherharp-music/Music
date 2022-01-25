using Microsoft.AspNetCore.Mvc;
using Music.Shared.Models;
using static Music.Server.Datas.Spreadsheet;
using static Music.Shared.Cores.Spreadsheet;

namespace Music.Server.Controllers;

[ApiController]
[Route("apis/[controller]")]
public class ShortController : ControllerBase
{
    [HttpGet]
    public async Task<IList<Short>> GetShorts()
    {
        var responseBody = await Service.Spreadsheets.Values
            .Get(MusicSpreadsheet.Id, MusicSpreadsheet.Range[nameof(Short).ToLower()]).ExecuteAsync();
        return Serializer.Deserialize<IList<Short>>(responseBody.Values);
    }
}