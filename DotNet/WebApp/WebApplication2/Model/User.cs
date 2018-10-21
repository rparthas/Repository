using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication2.Model
{
    public class User:IdentityUser<string>
    {
    
        [Required]
        public string Password { get; set; }
       
    }
}
