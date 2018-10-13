using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;

namespace WebApplication1.Controllers
{
    public class PagesController : Controller
    {
        public IActionResult Movies()
        {
            List<string> result = new List<String>();
            result.Add("Runaway Jury");
            result.Add("Gone Girl");
            result.Add("Shawshank");
            return View(result);
        }

        [Route("movieGuide",Name ="MovieGuide")]
        public IActionResult MovieGuide()
        {
            ViewData["movies"] = new List<String>()
            {
                "Dark Knight",
                "Gone Girl",
                "Shawshank Redemption"
            }; 
            return View();
        }
    }
}