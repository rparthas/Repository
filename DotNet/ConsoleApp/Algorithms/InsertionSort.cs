using System;

namespace Algorithms
{
    public class InsertionSort : ISorter
    {
        public int[] Sort(int[] numbers)
        {
            for (var j = 1; j < numbers.Length; j++)
            {
                var key = numbers[j];
                var i = j - 1;

                while (i >= 0 && numbers[i] > key)
                {
                    numbers[i + 1] = numbers[i];
                    i -= 1;
                }
                numbers[i + 1] = key;
            }

            return numbers;
        }
    }
}