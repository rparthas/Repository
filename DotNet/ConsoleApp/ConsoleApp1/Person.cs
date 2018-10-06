namespace ConsoleApp1 {
    public class Person {

        private string _name;
        private int _age;
        public string Name {
            get => _name;
        }

        public int Age {
            get {
                return _age;
            }
        }

        public Person (decimal salary) {
            this.Salary = salary;

        }
        private decimal Salary { get; set; }

        public Person (string name, int age, decimal salary = 45000) {
            _name = name;
            _age = age;
            Salary = salary;
        }

        virtual public void printDetails () {
            System.Console.WriteLine ($"I am {Name} aging {Age} with a salary of {Salary}");
        }

        override public string ToString () => $"I am {Name} aging {Age} with a salary of {Salary}";
    }
}