using System;
using Xunit;
using Algorithms;
using Xunit.Sdk;

namespace Algorithms.Test
{
    public class BinarySearchTest
    {
        private readonly BinarySearch _search = new BinarySearch();

        private static int[] GenerateNumbers(int count)
        {
            var numbers = new int[count];
            for (var i = 0; i < count; i++)
            {
                numbers[i] = i + 1;
            }

            return numbers;
        }

        [Fact]
        public void ShouldReturnIndexCorrectlyWhenElementIsPresent()
        {
            Assert.Equal(4, _search.Search(GenerateNumbers(10), 5));
            Assert.Equal(8, _search.Search(GenerateNumbers(10), 9));
        }

        [Fact]
        public void ShouldReturnNegative1WhenElementIsNotPresent() =>
            Assert.Equal(-1, _search.Search(GenerateNumbers(10), 11));
    }
}