namespace Inheritance{
    public class Person {

        private string _name;
        private int _age;

        private IRun _runner;

        public string Name {
            get => _name;
        }

        public int Age {
            get {
                return _age;
            }
        }

        private decimal Salary { get; set; }

        public Person (string name, int age, IRun runner, decimal salary = 45000) {
            _name = name;
            _age = age;
            Salary = salary;
            _runner = runner;
        }

        virtual public void printDetails () {
            System.Console.WriteLine ($"I am {Name} aging {Age} with a salary of {Salary}");
        }

        override public string ToString () => $"I am {Name} aging {Age} with a salary of {Salary}";

        public void Run (int distance) {
            _runner.Run (distance);
        }
    }
}