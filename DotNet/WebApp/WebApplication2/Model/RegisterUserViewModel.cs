using System.ComponentModel.DataAnnotations;

namespace WebApplication2.Model
{
    public class RegisterUserViewModel
    {
        [Required]
        [StringLength(maximumLength: 6,ErrorMessage = "Username Should have length atleast 6")]
        public string UserName { get; set; }

        [Required]
        [StringLength(maximumLength: 6, ErrorMessage = "Password Should have length atleast 6")]
        public string Password { get; set; }

        [Required]
        [EmailAddress]
        public string Email { get; set; }
    }
}