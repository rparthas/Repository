using System;
using System.Threading;

namespace Algorithms
{
    public class MergeSort : ISorter
    {
        public int[] Sort(int[] numbers)
        {
            MergeSorter(numbers, 0, numbers.Length - 1);
            return numbers;
        }

        private void MergeSorter(int[] numbers, int start, int end)
        {
            int middle = (end + start) / 2;
            if (start >= end)
            {
                return;
            }

            MergeSorter(numbers, start, middle);
            MergeSorter(numbers, middle + 1, end);
            Merge(numbers, start, middle, end);
        }


        private void Merge(int[] numbers, int start, int middle, int end)
        {
            var left = new int[middle - start + 1];
            var right = new int[end - middle];

            for (var k = 0; k < left.Length; k++)
            {
                left[k] = numbers[start + k];
            }

            for (var k = 0; k < right.Length; k++)
            {
                right[k] = numbers[middle + 1 + k];
            }

            var low = 0;
            var high = 0;

            for (var k = start; k <= end; k++)
            {
                if (low >= left.Length)
                {
                    numbers[k] = right[high];
                    high++;
                }
                else if (high >= right.Length || left[low] <= right[high])
                {
                    numbers[k] = left[low];
                    low++;
                }
                else
                {
                    numbers[k] = right[high];
                    high++;
                }
            }
        }
    }
}