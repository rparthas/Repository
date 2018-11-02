using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication2.Model
{
    public class ServerViewModel
    {
        [Required]
        [StringLength(10, ErrorMessage = "{0} must be altleast {2} and atmost {1}", MinimumLength =5)]
        [Display(Name="Server Name")]
        public string Name { get; set; }
        [Required]
        [Display(Name = "Server Location")]
        public string Location { get; set; }
        [Required]
        [Display(Name = "Server Status")]
        public string Status { get; set; }
    }
}
