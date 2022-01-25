using Microsoft.AspNetCore.Mvc;
using Music.Shared.Models;
using static Music.Server.Datas.Spreadsheet;
using static Music.Shared.Cores.Spreadsheet;

namespace Music.Server.Controllers;

[ApiController]
[Route("apis/[controller]")]
public class AudioController : ControllerBase
{
    [HttpGet]
    public async Task<IList<Audio>> GetAudios()
    {
        var responseBody = await Service.Spreadsheets.Values
            .Get(MusicSpreadsheet.Id, MusicSpreadsheet.Range[nameof(Audio).ToLower()]).ExecuteAsync(); 
        return Serializer.Deserialize<IList<Audio>>(responseBody.Values);
    }
}