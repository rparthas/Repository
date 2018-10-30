using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using WebApplication2.Model;

namespace WebApplication2.Controllers
{
    [Authorize]
    public class ServerController:Controller
    {
        
        public IActionResult Index()
        {
            ViewData["Title"] = "Servers";
            List<Server> servers = new List<Server>();
            servers.Add(new Server()
            {
                Name="ABC",
                Location="Asia",
                Status="Online"
            });
            return View("Servers",servers);
        }

        
        public IActionResult AddServer()
        {
            ViewData["Title"] = "Servers Add";
            return View("Servers");
        }
    }
}
