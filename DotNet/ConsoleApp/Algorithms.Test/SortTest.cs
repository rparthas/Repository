using System;
using Xunit;
using Algorithms;

namespace Algorithms.Test
{
    public class SortTest
    {
        [Fact]
        public void InsertionSortTest() => AssertSortNumbers(new InsertionSort());
        
        [Fact]
        public void MergeSortTest() => AssertSortNumbers(new MergeSort());
        

        private static void AssertSortNumbers(ISorter sorter)
        {
            var numbers = new int[]
            {
                8, 7, 6, 5, 4, 3, 2, 1
            };
            numbers = sorter.Sort(numbers);
            int counter = 1;
            foreach (var number in numbers)
            {
                Assert.Equal(counter++, number);
            }
        }
    }
}