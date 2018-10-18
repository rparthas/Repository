using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication1.Services
{
    public class MovieService : IMovieService<string>
    {
        public IEnumerable<string> GetMovie()
        {
            List<string> result = new List<String>();
            result.Add("Runaway Jury");
            result.Add("Gone Girl");
            result.Add("Shawshank");
            return result;
        }
    }
}
