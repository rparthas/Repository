using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace WebApplication2.Controllers
{
    [Authorize]
    public class ServerController:Controller
    {
        
        public IActionResult Index()
        {
            ViewData["Title"] = "Servers";
            return View("Servers");
        }

        
        public IActionResult AddServer()
        {
            ViewData["Title"] = "Servers Add";
            return View("Servers");
        }
    }
}
