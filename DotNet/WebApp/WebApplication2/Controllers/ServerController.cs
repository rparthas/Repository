using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Threading.Tasks;
using WebApplication2.Model;
using WebApplication2.Services;

namespace WebApplication2.Controllers
{
    [Authorize]
    [Route("[controller]/[Action]/[id?]")]
    public class ServerController:Controller
    {
        IServerDataService _serverDataService;

        public ServerController(IServerDataService serverDataService)
        {
            _serverDataService = serverDataService;
        }
        
        public async Task<IActionResult> Index()
        {
            ViewData["Title"] = "Servers";
            IEnumerable<ServerViewModel> servers = await _serverDataService.FindServer(new ServerViewModel());
            return View("Index",servers);
        }

        [HttpGet]
        public IActionResult AddServer()
        {
            ViewData["Title"] = "Add Servers";
            return View("AddServer");
        }

        [HttpPost]
        public IActionResult AddServer([Bind]ServerViewModel serverViewModel)
        {

            ViewData["Title"] = "Servers";
            _serverDataService.AddServer(serverViewModel);
            return View("Index");
        }
    }
}
