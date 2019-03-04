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
            const int count = 10;
            var numbers = new int[count];
            for (var i = count; i >= 1; i--)
            {
                numbers[count - i] = i;
            }
            numbers = sorter.Sort(numbers);
            var counter = 1;
            foreach (var number in numbers)
            {
                Assert.Equal(counter++, number);
            }
        }
    }
}