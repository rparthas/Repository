using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using WebApplication2.Model;

namespace WebApplication2.Controllers
{

    public class AccountController : Controller
    {
        private readonly UserManager<User> _userManger;

        private readonly SignInManager<User> _signInManager;

        private readonly ILogger _logger;

        public AccountController(UserManager<User> userManager, SignInManager<User> signInManager, ILogger<AccountController> logger)
        {
            _userManger = userManager;
            _signInManager = signInManager;
            _logger = logger;
        }

        public IActionResult Login(string returnUrl)
        {
            ViewData["Title"] = "Login";
            ViewData["ReturnUrl"] = returnUrl;
            return View("Login");
        }

        [HttpPost]
        [AllowAnonymous]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Login([Bind]LoginViewModel loginViewModel, string returnUrl = null)
        {
            ViewData["ReturnUrl"] = returnUrl;
            if (ModelState.IsValid)
            {
                User user = new User()
                {
                    UserName = loginViewModel.UserName
                };
                var result = await _signInManager.PasswordSignInAsync(userName:loginViewModel.UserName, password: loginViewModel.Password, isPersistent: false, lockoutOnFailure: false);
                if (result.Succeeded)
                {
                    return Redirect(returnUrl);
                }
               
            }
            return View("Login");
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Register([Bind]RegisterUserViewModel registerUserViewModel, string ReturnUrl = null)
        {
            ViewData["Title"] = "Register";
            ViewData["ReturnUrl"] = ReturnUrl;
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
                    return Redirect(ReturnUrl);
                }
            }
            ViewBag.Error = "Unable to register";
            return View("Register");
        }

        public IActionResult Register(string returnUrl)
        {
            ViewData["ReturnUrl"] = returnUrl;
            return View("Register");
        }


        public async Task<IActionResult> Logout()
        {
            await _signInManager.SignOutAsync();
            return Redirect("/");
        } 

    }
}
