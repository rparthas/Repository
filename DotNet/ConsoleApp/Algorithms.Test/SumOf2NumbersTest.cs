using System;
using Xunit;
using Algorithms;
using Xunit.Sdk;

namespace Algorithms.Test
{
    public class SumOf2NumbersTest
    {
        private readonly SumOf2Numbers _sumOf2Numbers = new SumOf2Numbers();

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
        public void ShouldReturnElementsWhenSumIsPresent()
        {
            var numbers = GenerateNumbers(10);
            var arr = _sumOf2Numbers.GetMatchingNumbers(numbers, 10);
            Assert.Equal(2, arr.Length);
            Assert.Equal(1,arr[0]);
            Assert.Equal(9,arr[1]);
            
            arr = _sumOf2Numbers.GetMatchingNumbers(numbers, 15);
            Assert.Equal(2, arr.Length);
            Assert.Equal(5,arr[0]);
            Assert.Equal(10,arr[1]);

        }
        
        [Fact]
        public void ShouldReturnEmptyWhenSumIsNotPresent()
        {
            var numbers = GenerateNumbers(10);
            var arr = _sumOf2Numbers.GetMatchingNumbers(numbers, 20);
            Assert.Empty(arr);           
        }
    }
}