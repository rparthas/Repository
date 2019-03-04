using System;
using System.Diagnostics;
using System.Linq;

namespace Algorithms
{
    public class Executor
    {
        
        static void Main(string[] args)
        {
            
            ExecuteSort(new InsertionSort());
            ExecuteSort(new MergeSort());
        }


        private static void ExecuteSort(ISorter sorter)
        {
            var numbers = GenerateRandomNumbers(100000);
            Stopwatch stopWatch = new Stopwatch();
            stopWatch.Start();
            var result = sorter.Sort(numbers);
            PrintElapsed(stopWatch);
        }

        private static int[] GenerateRandomNumbers(int count)
        {
            
            Random randNum = new Random();
            int[] numbers = Enumerable
                .Repeat(0, count)
                .Select(i => randNum.Next(1, count))
                .ToArray();

            return numbers;
        }

        private static void PrintElapsed(Stopwatch stopWatch)
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