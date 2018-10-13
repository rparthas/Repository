using Microsoft.AspNetCore.Http;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication1.Middleware
{
    public class ErrorMiddleware
    {

        private readonly RequestDelegate _next; 

        public ErrorMiddleware(RequestDelegate next)
        {
            _next = next;
        }

        public async Task Invoke(HttpContext context)
        {
            try
            {
                await _next(context);
                //await context.Response.WriteAsync("Success is great");
            }
            catch (Exception)
            {
                await context.Response.WriteAsync("Oops Error generated");
            }
        }
    }

}
