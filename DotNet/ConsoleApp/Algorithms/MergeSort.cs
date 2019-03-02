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
            int middle = (end + start ) / 2;
            if (start >= end)
            {
                return;
            }

            MergeSorter(numbers, start, middle);
            MergeSorter(numbers, middle+1, end);
            Merge(numbers,start,middle,end);
        }


        private void Merge(int[] numbers, int start,int middle, int end)
        {
            var left = new int[middle - start + 2];
            var right = new int[end - middle+1];
            left[left.Length - 1] = int.MaxValue;
            right[right.Length - 1] = int.MaxValue;
            
            for (int k = 0; k < left.Length-1; k++)
            {
                left[k] = numbers[start + k];
            }
            for (int k = 0; k < right.Length-1; k++)
            {
                right[k] = numbers[middle+1 + k];
            }
            int low = 0;
            int high = 0;
            
            for (int k = start; k <= end; k++)
            {
                if (left[low] <= right[high])
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