namespace Algorithms
{
    public class BinarySearch
    {
        public int Search(int[] numbers, int numberToFind)
        {
            var mid = numbers.Length / 2;
            for (int start = 0, end = numbers.Length;
                start <= end && start + mid < numbers.Length && end - mid >= 0;
                mid = (end - start) / 2)
            {
                if (numbers[start + mid] == numberToFind)
                    return start + mid;

                if (numbers[start + mid] > numberToFind)
                    end = start + mid;
                else
                    start = end - mid;
            }

            return -1;
        }
    }
}