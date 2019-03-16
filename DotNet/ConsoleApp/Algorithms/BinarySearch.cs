namespace Algorithms
{
    public class BinarySearch
    {
        public int Search(int[] numbers, int numberToFind)
        {
            var start = 0;
            var end = numbers.Length;

            while (start <= end)
            {
                var mid = (end - start) / 2;
                if (start + mid >= numbers.Length || end - mid < 0)
                {
                    return -1;
                }

                if (numbers[start + mid] == numberToFind)
                {
                    return start + mid;
                }

                if (numbers[start + mid] > numberToFind)
                {
                    end = start + mid;
                    continue;
                }

                start = end - mid;
            }

            return -1;
        }
    }
}