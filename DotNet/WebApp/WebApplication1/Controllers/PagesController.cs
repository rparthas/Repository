using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using WebApplication1.Services;

namespace WebApplication1.Controllers
{
    public class PagesController : Controller
    {
        private IMovieService<string> _movieService;

        public PagesController(IMovieService<string> movieService)
        {
            _movieService = movieService;
        }
        public IActionResult Movies() => View(_movieService.GetMovie());
       

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


        public HttpResponseMessage Index()
        {
            return new HttpResponseMessage()
            {
                Content = new StringContent(
                   "<strong>test</strong>",
                   Encoding.UTF8,
                   "text/html"
                    )
            };
        }
    }
}