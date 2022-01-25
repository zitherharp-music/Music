using Google.Apis.Sheets.v4.Data;
using Microsoft.AspNetCore.Mvc;
using Music.Shared.Models;
using static Music.Server.Datas.Spreadsheet;
using static Music.Shared.Cores.Spreadsheet;
using AppendValueInputOption = 
    Google.Apis.Sheets.v4.SpreadsheetsResource.ValuesResource.AppendRequest.ValueInputOptionEnum;
using UpdateValueInputOption = 
    Google.Apis.Sheets.v4.SpreadsheetsResource.ValuesResource.UpdateRequest.ValueInputOptionEnum;

namespace Music.Server.Controllers;

//[ApiController]
//[Route("apis/[controller]")]
//public class TelevisionController : ControllerBase
//{
//    protected static readonly string id = JsonValues.Id["television"];
//    protected static readonly IDictionary<string, string> range = JsonValues.Range["television"];

//    [ApiController]
//    public class InformationController : TelevisionController
//    {
//        [HttpGet]
//        public async Task<IList<string?>> GetInformations()
//        {
//            var responseBody = await Service.Spreadsheets.Values
//                .Get(id, range["information"]).ExecuteAsync();
//            var informations = new List<string?>();
//            foreach (var row in responseBody.Values)
//            {
//                informations.Add(row[0].ToString());
//            }
//            return informations;
//        }
//    }

//    [ApiController]
//    public class PlaylistController : TelevisionController
//    {
//        private static readonly new string range = TelevisionController.range["playlist"];

//        [HttpGet]
//        public async Task<IList<Audio>> GetPlaylist()
//        {
//            var responseBody = await Service.Spreadsheets.Values.Get(id, range).ExecuteAsync();
//            Audio audio;
//            var audios = new List<Audio>();
//            var columns = JsonValues.Column["audio"];
//            foreach (var value in responseBody.Values)
//            {
//                audio = new Audio()
//                {
//                    Id = value[columns["id"]].ToString(),
//                    ArtistId = value[columns["artistId"]].ToString(),
//                    VietnameseName = value[columns["vietnameseName"]].ToString(),
//                    ChineseName = value[columns["chineseName"]].ToString(),
//                    Duration = (int)value[columns["duration"]],
//                };
//                audios.Add(audio);
//            }
//            return audios;
//        }

//        [HttpPost]
//        public async Task<string> AppendPlaylist([FromBody] IList<Audio> audios)
//        {
//            var rows = new List<IList<object?>>();
//            foreach (var audio in audios)
//            {
//                var row = new List<object?>
//            {
//                audio.Id,
//                audio.ArtistId,
//                audio.VietnameseName,
//                audio.ChineseName,
//                audio.Duration
//            };
//                rows.Add(row);
//            }
//            var requestBody = new ValueRange() { Values = rows };
//            var request = Service.Spreadsheets.Values.Append(requestBody, id, range);
//            request.ValueInputOption = AppendValueInputOption.RAW;
//            var response = await request.ExecuteAsync();
//            return response.TableRange;
//        }

//        [HttpPut]
//        public async Task<string> UpdatePlaylist([FromBody] IList<Audio> audios)
//        {
//            var rows = new List<IList<object?>>();
//            foreach (var audio in audios)
//            {
//                var row = new List<object?>
//                {
//                    audio.Id,
//                    audio.ArtistId,
//                    audio.VietnameseName,
//                    audio.ChineseName,
//                    audio.Duration
//                };
//                rows.Add(row);
//            }
//            var requestBody = new ValueRange() { Values = rows };
//            var request = Service.Spreadsheets.Values.Update(requestBody, id, range);
//            request.ValueInputOption = UpdateValueInputOption.RAW;
//            var response = await request.ExecuteAsync();
//            return response.UpdatedRange;
//        }
//    }

//    [ApiController]
//    public class PlayingaudioController : TelevisionController
//    {
//        [HttpGet]
//        public async Task<Audio> GetPlayingaudio()
//        {
//            var responseBody = await Service.Spreadsheets.Values
//                .Get(id, JsonValues.Range["playlist"]).ExecuteAsync();
//            return Serializer.Deserialize<IList<Audio>>(responseBody.Values)[0];
//        }

//        [HttpPost]
//        public async Task<string> AppendPlayingaudio([FromBody] Audio audio)
//        {
//            var row = new List<IList<object?>>()
//        {
//            new List<object?>()
//            {
//                audio.Id,
//                audio.ArtistId,
//                audio.VietnameseName,
//                audio.ChineseName,
//                audio.Duration
//             }
//        };
//            var requestBody = new ValueRange() { Values = row };
//            var request = Service.Spreadsheets.Values
//                .Append(requestBody, id, JsonValues.Range["playingaudio"]);
//            request.ValueInputOption = AppendValueInputOption.RAW;
//            var response = await request.ExecuteAsync();
//            return response.TableRange;
//        }

//        [HttpPut]
//        public async Task<string> UpdatePlayingaudio([FromBody] Audio audio)
//        {
//            var row = new List<IList<object?>>()
//        {
//            new List<object?>()
//            {
//                audio.Id,
//                audio.ArtistId,
//                audio.VietnameseName,
//                audio.ChineseName,
//                audio.Duration
//             }
//        };
//            var requestBody = new ValueRange() { Values = row };
//            var request = Service.Spreadsheets.Values
//                .Update(requestBody, id, JsonValues.Range["playingaudio"]);
//            request.ValueInputOption = UpdateValueInputOption.RAW;
//            var response = await request.ExecuteAsync();
//            return response.UpdatedRange;
//        }
//    }

//    [ApiController]
//    public class MessageController : TelevisionController
//    {
//        [HttpGet]
//        public async Task<IList<Message>> GetMessages()
//        {
//            var responseBody = await Service.Spreadsheets.Values
//                .Get(id, JsonValues.Range["message"]).ExecuteAsync();
//            var messages = new List<Message>();
//            foreach (var row in responseBody.Values)
//            {
//                var message = new Message()
//                {
//                    Id = row[0].ToString(),
//                    Content = row[1].ToString(),
//                    User = new User()
//                    {
//                        Id = row[2].ToString(),
//                        Name = row[3].ToString()
//                    }
//                };
//                messages.Add(message);
//            }
//            return messages;
//        }

//        [HttpPost]
//        public async Task<string> AppendMessage([FromBody] Message message)
//        {
//            var row = new List<IList<object?>>()
//        {
//            new List<object?>()
//            {
//                message.Id,
//                message.Content,
//                message.User?.Id,
//                message.User?.Name
//             }
//        };
//            var requestBody = new ValueRange() { Values = row };
//            var request = Service.Spreadsheets.Values
//                .Append(requestBody, id, JsonValues.Range["message"]);
//            request.ValueInputOption = AppendValueInputOption.RAW;
//            var response = await request.ExecuteAsync();
//            return response.TableRange;
//        }
//    }
//}