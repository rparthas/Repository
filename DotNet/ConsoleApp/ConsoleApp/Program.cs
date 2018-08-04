using System;

namespace ConsoleApp
{
    class MainClass
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("Hello World");
            const byte a = 10;
            var b = 3;
            Console.WriteLine((float)a / (float)b);
            checked
            {
                byte number = 254;
                number += 1;
                Console.WriteLine(number);
            }
            Console.WriteLine("{0}...{1}", byte.MinValue, byte.MaxValue);
            int i = Convert.ToInt32("1");
            i = int.Parse("1");
            Console.WriteLine("number is {0}", i);
            try
            {
                var num = "1234";
                Console.WriteLine(byte.Parse(num));
            }
            catch (Exception)
            {
                Console.WriteLine("failed byte conversion");
            }
        }
    }
}
