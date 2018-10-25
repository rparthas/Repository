using System;
using System.Collections.Generic;
using System.Linq;
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


        public IActionResult Index()
        {
            ViewData["Title"] = "Login";
            return View("Login");
        }

        public IActionResult Login([Bind]User user)
        {
            return ModelState.IsValid ?
                    Content("Logged in Successfully") : (IActionResult)View("Login");
        }

        [Route("Register")]
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
                    return Content("Registered Successfully");
                }
            }
            ViewBag.Error = "Unable to register";
            return View("Register");
        }

        [Route("RegisterForm")]
        public IActionResult RegisterForm() => View("Register");

    }
}
