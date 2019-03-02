using System;
using System.Diagnostics;

namespace Algorithms
{
    public class Executor
    {
        
        static void Main(string[] args)
        {
            
            ExecuteSort(new InsertionSort());
            ExecuteSort(new MergeSort());
        }


        public static void ExecuteSort(ISorter sorter)
        {
            var numbers = GenerateRandomNumbers(100000);
//            PrintResult(numbers, "Input:");
            Stopwatch stopWatch = new Stopwatch();
            stopWatch.Start();
            var result = sorter.Sort(numbers);
            PrintElapsed(stopWatch);
//            PrintResult(result, "Sorted Output:");
        }

        private static int[] GenerateRandomNumbers(int count)
        {
            Random rnd = new Random();
            var numbers = new int[count];
            for (var index = 0; index < count; index++)
            {
                numbers[index] = rnd.Next(1, count);
            }

            return numbers;
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

        static void PrintElapsed(Stopwatch stopWatch)
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