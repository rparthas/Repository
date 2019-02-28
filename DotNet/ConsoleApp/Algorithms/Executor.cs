using System;
using System.Diagnostics;

namespace Algorithms
{
    public class Executor
    {
        static Stopwatch stopWatch = new Stopwatch();
        static void Main(string[] args)
        {
            var count = 100000;
            Random rnd = new Random();
            var numbers = new int[count];
            for (var index = 0; index < count; index++)
            {
                numbers[index] = rnd.Next(1, count);
            }
            ExecuteSort(new InsertionSort(), numbers);
        }


        public static void ExecuteSort(ISorter sorter,int[] numbers)
        {
            PrintResult(numbers, "Input:");
            stopWatch.Start();
            var result = sorter.Sort(numbers);
            PrintElapsed();
            PrintResult(result, "Sorted Output:");
        }


        public static void PrintResult(int[] numbers, string message)
        {
            Console.WriteLine(message);
            foreach (var number in numbers)
            {
                Console.Write(number + " ");
            }

            Console.WriteLine("");
        }

        static void PrintElapsed()
        {
            stopWatch.Stop();
            TimeSpan ts = stopWatch.Elapsed;
            string elapsedTime = String.Format("{0:00}:{1:00}:{2:00}.{3:00}",
                ts.Hours, ts.Minutes, ts.Seconds,
                ts.Milliseconds / 10);
            Console.WriteLine("RunTime " + elapsedTime);
        }
    }
}