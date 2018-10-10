using System;
using System.Collections.Generic;
using System.Text;

namespace NewFeatures7._0
{
    class Person
    {
        public string name { get; private set; }

        private int _age;

        public int Age { get => _age; private set => _age = value; }


        public Person(string name,int age)
        {
            this.name = name ?? throw new ArgumentNullException("Name cannot be null");
            _age = age;
        }

        public ref int GetAge() => ref _age;

        public override string ToString() => $"Name:{name} Age:{_age}";
    }

    class Student : Person
    {
        public Student(string name, int age) :base(name, age)
        {

        }
    }

    class Employee : Person
    {
        public Employee(string name, int age) : base(name, age)
        {

        }
    }
}
