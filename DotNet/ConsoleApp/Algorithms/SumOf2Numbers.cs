using System;

namespace Algorithms
{
    /**
     * Exercise 2.3.7 of Introduction to CLR
     */
    public class SumOf2Numbers
    {
        public int[] GetMatchingNumbers(int[] numbers, int sum)
        {
            ISorter sorter = new MergeSort();
            var sortedNumbers = sorter.Sort(numbers);
            var search = new BinarySearch();
            for (var index = 0; index < sortedNumbers.Length; index++)
            {
                var returnIndex = search.Search(sortedNumbers, sum - sortedNumbers[index]);
                if (returnIndex != -1 && returnIndex != index)
                {
                    return new int[]
                    {
                        sortedNumbers[index], sortedNumbers[returnIndex]
                    };
                }
            }

            return new int[0];
        }
    }
}