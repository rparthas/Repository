using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace WebApplication2.Controllers
{
    [Route("Server")]
    public class ServerController:Controller
    {
        [Route("Index")]
        [Authorize]
        public IActionResult Index()
        {
            ViewData["Title"] = "Servers";
            return View();
        }
    }
}
