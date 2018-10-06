using System;

namespace ConsoleApp1
{
    class MainClass
    {
        public static void Main(string[] args)
        {
            //string message = "Hello World";
            //Console.WriteLine(message);

            //Console.WriteLine("\a");

            //Console.Write("Enter your name ");
            //string name = Console.ReadLine();
            //Console.WriteLine("{0} is a sweet name",name);

            Class1 class1 = new Class1();
            class1.method1(3, "Hello");
            class1.myProperty = "hello";
            class1.IntProperty = 4;
            Console.WriteLine(class1.myProperty);
            Console.WriteLine(class1.IntProperty);

            //Console.WriteLine("Are Strings equal ? {0}", string.Compare("Hi", "hi", true));

            //Console.Write("Enter your number ");
            //string input = Console.ReadLine();
            //Console.WriteLine(
            //    double.TryParse(input, out double outNumber)
            //        ? $"Entered is a number {outNumber}"
            //        : $"Please enter a number {input}");

            //var b = 3;
            //Console.WriteLine((float)10 / (float)b);
            //checked
            //{
            //    byte number = 254;
            //    number += 1;
            //    Console.WriteLine(number);
            //    Console.WriteLine(number.GetType());
            //}
            //Console.WriteLine("{0}...{1}", byte.MinValue, byte.MaxValue);
            //int i = Convert.ToInt32("1");
            //i = int.Parse("1");
            //Console.WriteLine("number is {0}", i);
            //try
            //{
            //    const string Num = "1234";
            //    Console.WriteLine(byte.Parse(Num));
            //}
            //catch (Exception)
            //{
            //    Console.WriteLine("failed byte conversion");
            //}
            method();
            Console.ReadKey();
        }

        static void method()
        {
            char[] charArray = new char[4];
            Console.WriteLine($"Length:{charArray.Length}");
            charArray[0] = 'H';
            charArray[1] = 'E';
            charArray[2] = 'L';
            charArray[3] = 'L';
            foreach (var chars in charArray)
            {
                Console.WriteLine($"{chars}");
            }
        }
    }
}
