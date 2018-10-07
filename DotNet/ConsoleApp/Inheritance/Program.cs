using System;

namespace Inheritance
{
    class Program
    {
        static void Main(string[] args)
        {
            ClassMethod();
            InheritanceMethod();
            StructMethod();
            Console.ReadKey();
        }

        static void StructMethod()
        {
            Coordinates coordinates = new Coordinates();
            coordinates.latitude = 3.45;
            coordinates.longitude = 4.25;
            Console.WriteLine(coordinates);
            changeCoordinates(coordinates);
            Console.WriteLine(coordinates);
        }

        private static void changeCoordinates(Coordinates coordinates)
        {
            coordinates.latitude = coordinates.longitude;
        }

        static void InheritanceMethod()
        {
            Student student = new Student("Ram", 31, Student.UniversityTier.Tier1, 4.9, new SlowRunner());
            student.printDetails();
            System.Console.WriteLine(student);
            student.Run(5);
            switch (student.University)
            {
                case Student.UniversityTier.Tier1:
                    Console.WriteLine("Tier1 College");
                    break;
                case Student.UniversityTier.Tier2:
                    Console.WriteLine("Tier2 College");
                    break;
                case Student.UniversityTier.Tier3:
                    Console.WriteLine("Tier3 College");
                    break;
                default:
                    break;
            }
            Student appu = new Student("Appu", 28, Student.UniversityTier.Tier1, 4.9, new SlowRunner());
            Console.WriteLine($"Student Type : {(appu as Person).GetType()}");
            Console.WriteLine($"Student is instance of Person ? {student is Person}");
        }

        static void ClassMethod()
        {
            Class1 class1 = new Class1();
            class1.method1(3, "Hello");
            class1.myProperty = "hello";
            class1.IntProperty = 4;
            Console.WriteLine(class1.myProperty);
            Console.WriteLine(class1.IntProperty);
        }
    }
}
