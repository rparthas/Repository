using System;

namespace Exercises
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
            String max ="Hi There";
            Console.WriteLine(max);

            Console.Write("Enter your first name: ");
            String firstName = Console.ReadLine();

            Console.WriteLine($"Your first name is {firstName}");
            Console.WriteLine("Your first name is {0}",firstName);
        }
    }
}
