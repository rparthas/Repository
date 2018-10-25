using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using WebApplication2.Model;

namespace WebApplication2.Controllers
{
    public class LoginController : Controller
    {
        private readonly UserManager<User> _userManger;

        private readonly SignInManager<User> _signInManager;

        public LoginController(UserManager<User> userManager,SignInManager<User> signInManager)
        {
            _userManger = userManager;
            _signInManager = signInManager;
        }

        [AutoValidateAntiforgeryToken]
        public IActionResult Index()
        {
            ViewData["Title"] = "Login";
            return View("Login");
        }

        [Route("Login")]
        [AutoValidateAntiforgeryToken]
        [HttpPost]
        public IActionResult Login([Bind]LoginViewModel loginViewModel)
        {
            if (ModelState.IsValid)
            {
                User user = new User()
                {
                    UserName = loginViewModel.UserName
                };
                _signInManager.PasswordSignInAsync(user:user,password:loginViewModel.Password, isPersistent: false,lockoutOnFailure:false);
                return RedirectToAction("Index", "Server");
            }
            return View("Login");
        }

        [Route("Register")]
        [AutoValidateAntiforgeryToken]
        [HttpPost]
        public async Task<IActionResult> Register([Bind]RegisterUserViewModel registerUserViewModel)
        {
            ViewData["Title"] = "Register";
            if (ModelState.IsValid)
            {
                User user = new User()
                {
                    UserName = registerUserViewModel.UserName,
                    Email = registerUserViewModel.Email
                };
                var result = await _userManger.CreateAsync(user, registerUserViewModel.Password);
                if (result.Succeeded)
                {
                    await _signInManager.SignInAsync(user: user, isPersistent: false);
                    ViewBag.Error = "";
                    return RedirectToAction("Index", "Server");
                }
            }
            ViewBag.Error = "Unable to register";
            return View("Register");
        }

        [Route("RegisterForm")]
        [AutoValidateAntiforgeryToken]
        public IActionResult RegisterForm() => View("Register");


        [Route("Logout")]
        public async Task<IActionResult> Logout()
        {
            await _signInManager.SignOutAsync();
            return View("Login");
        } 

    }
}
