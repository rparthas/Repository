using System.Collections.Generic;
using System.Threading.Tasks;
using WebApplication2.Model;

namespace WebApplication2.Services
{
    public interface IServerDataService
    {
        bool AddServer(ServerViewModel server);

        Task<IEnumerable<ServerViewModel>> FindServer(ServerViewModel server);
    }
}
