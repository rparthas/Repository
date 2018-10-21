using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication1.Model
{
    public class PaymentInfo
    {
        [Required]
        [StringLength(10, MinimumLength = 4)]
        public string Name { get; set; }

        [Required]
        [Range(500,1000)]
        public string Amount { get; set; }

        public override string ToString()
        {
            return $"Name:{Name} Amount:{Amount}";
        }
    }
}
