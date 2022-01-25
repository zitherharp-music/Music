using Microsoft.AspNetCore.Mvc;
using static Music.Shared.Cores.Spreadsheet;

namespace Music.Server.Controllers;

[ApiController()]
[Route("apis/[controller]")]
public class MusicController : ControllerBase
{
    protected readonly ILogger<MusicController> logger;

    public MusicController(ILogger<MusicController> logger)
    {
        this.logger = logger;
    }
}