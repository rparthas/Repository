﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication2.Model
{
    public class Server
    {
        [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public Guid Id { get; set; }
        [Required]
        [StringLength(10, ErrorMessage = "{0} must be altleast {2} and atmost {1}", MinimumLength = 5)]
        public string Name { get; set; }
        [Required]
        public string Location { get; set; }
        [Required]
        public string Status { get; set; }

    }
}
