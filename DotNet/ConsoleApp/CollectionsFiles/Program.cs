using System.Collections.Generic;
using System;

namespace CollectionsFiles
{
    class Program
    {
        static void Main(string[] args)
        {
            GenricMethod();
            Console.ReadKey();
        }

        private static void GenricMethod()
        {
            Stack<string> stringStack = new Stack<string>();
            stringStack.push("Henry");
            stringStack.push("Jones");
            stringStack.push("Junior");
            Console.WriteLine($"Popped item is {stringStack.pop()}");
            Console.WriteLine($"Popped item is {stringStack.pop()}");
            Console.WriteLine($"Popped item is {stringStack.pop()}");

            Random random = new Random();
            LinkedList<Decimal> items = new LinkedList<decimal>();
            items.AddFirst(random.Next());
            items.AddFirst(random.Next());
            items.AddFirst(random.Next());

            foreach (decimal item in items)
            {
                Console.WriteLine($"Item is {item}");
            }

            var stack = items.ToStack();
            while(stack.TryPop(out var result)){
                System.Console.WriteLine($"Popped item is {result}");
            }
        }
    }
}
