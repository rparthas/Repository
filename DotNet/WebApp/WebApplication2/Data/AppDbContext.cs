using JetBrains.Annotations;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApplication2.Model;

namespace WebApplication2.Data
{
    public class AppDBContext : DbContext
    {
        public AppDBContext(DbContextOptions options) : base(options)
        {
        }

        public DbSet<User> Users { get; set; }

        public DbSet<IdentityUserRole<string>> IdentityUserRole { get; set; }  

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {   modelBuilder.Entity<IdentityUserRole<string>>().HasKey(p => new { p.UserId });
         
        }


    }
}
