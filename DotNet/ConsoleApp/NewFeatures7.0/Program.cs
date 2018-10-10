using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace NewFeatures7._0
{
    class Program
    {
        static void Main(string[] args)
        {
            //TupleMethod();
            //PatternMatchingMethod();
            //RefMethod();
            //ThrowExceptionMethod();
            //GetAsyncTask();

            int binary = 0b1000_0100;
            Console.WriteLine(binary);

            Console.ReadKey();
        }

        private static async ValueTask<bool> GetAsyncTask()
        {
            await Task.Delay(1000);
            return false;
        }

        private static void ThrowExceptionMethod()
        {
            try
            {
                Student s = new Student(null, 2);
            }
            catch (ArgumentNullException e)
            {
                Console.WriteLine(e.Message);
            }
        }

        private static void RefMethod()
        {
            Person person = new Student("1", 12);
            int age = person.Age;
            add(ref age);

            Console.WriteLine($"Person is {person}");

            ref int refAge = ref person.GetAge();
            add(ref refAge);
            Console.WriteLine($"Person is {person}");

            void add(ref int number)
            {
                number++;
            }
        }

        private static void PatternMatchingMethod()
        {
            List<Person> people = new List<Person>();
            people.Add(new Student("1", 12));
            people.Add(new Employee("2", 27));
            people.Add(new Employee("3", 35));
            people.Add(new Student("4", 19));

            foreach (var person in people)
            {
                switch (person)
                {
                    case Student s when s.Age <= 17:
                        Console.WriteLine($"{person.name} is school student");
                        break;
                    case Student s when s.Age > 18:
                        Console.WriteLine($"{person.name} is college student");
                        break;
                    case Employee e when e.Age <= 30:
                        Console.WriteLine($"{person.name} is Junior Employee");
                        break;
                    case Employee e when e.Age > 30:
                        Console.WriteLine($"{person.name} is Senior Employee");
                        break;
                    default:
                        break;
                }
            }
        }

        private static void TupleMethod()
        {
            var result = AddAndDivide(6, 3);
            Console.WriteLine($"Sum is {result.sum} and division is {result.div}");
            int sum;
            double div;
            (sum, div) = AddAndDivide(6, 3);
            Console.WriteLine($"Sum is {sum} and division is {div}");
        }

        static (int sum, double div) AddAndDivide(int a, int b) => (a + b, a / b);
    }
}
