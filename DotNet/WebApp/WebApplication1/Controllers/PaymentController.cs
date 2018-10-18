﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using WebApplication1.Model;

namespace WebApplication1.Controllers
{
    public class PaymentController : Controller
    {
        public IActionResult Index()
        {
            return View("Create");
        }

        [HttpPost]
        public ActionResult AddPayment(PaymentInfo paymentInfo)
        {
            ViewData["Payment"] = ModelState.IsValid;
            ViewData["Name"] = paymentInfo.name;
            return View("Success");
        }
    }
}