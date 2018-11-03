using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApplication2.Data;
using WebApplication2.Model;

namespace WebApplication2.Services
{
    public class ServerDataService : IServerDataService
    {
        private AppDBContext _dbContext;

        public ServerDataService(AppDBContext dbContext)
        {
            _dbContext = dbContext;
        }
        public bool AddServer(ServerViewModel serverViewModel)
        {
            try
            {
                Server server = new Server
                {
                    Name = serverViewModel.Name,
                    Location = serverViewModel.Location,
                    Status = serverViewModel.Status
                };
                _dbContext.Add<Server>(server);
                _dbContext.SaveChanges();
                return true;
            }
            catch (Exception) { }

            return false;
        }

        public async Task<IEnumerable<ServerViewModel>> FindServer(ServerViewModel serverViewModel)
        {


            List<ServerViewModel> servers = new List<ServerViewModel>();

            using (_dbContext)
            {
                List<Server> _servers = await FindServers(serverViewModel);
                Parallel.ForEach(_servers, (server) =>
                {
                    ServerViewModel viewModel = new ServerViewModel
                    {
                        Name = server.Name,
                        Status = server.Status,
                        Location = server.Location
                    };
                    servers.Add(viewModel);
                });
            }



            return servers;
        }

        private async Task<List<Server>> FindServers(ServerViewModel serverViewModel)
        {
            return await (from s in _dbContext.Servers
                          where (serverViewModel.Name == null || s.Name == serverViewModel.Name)
                          && (serverViewModel.Status == null || s.Status == serverViewModel.Status)
                          && (serverViewModel.Location == null || s.Location == serverViewModel.Location)
                          select s).ToListAsync();
        }
    }
}
