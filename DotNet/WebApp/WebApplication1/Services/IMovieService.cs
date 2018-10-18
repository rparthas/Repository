using System.Collections.Generic;

namespace WebApplication1.Services
{
    public interface IMovieService<T>
    {
        IEnumerable<T> GetMovie();
    }
}