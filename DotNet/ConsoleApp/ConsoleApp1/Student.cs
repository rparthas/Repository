namespace ConsoleApp1 {
    public class Student : Person {

        private string _university;
        private double _grade;
        public Student (string name, int age, string university, double grade, IRun runner, decimal salary = 2500) : base (name, age, runner, salary) {
            University = university;
            Grade = grade;
        }

        public double Grade { get => _grade; set => _grade = value; }
        public string University { get => _university; set => _university = value; }

        override public void printDetails () {
            base.printDetails ();
            System.Console.WriteLine ($"Also I have  {Grade} in {University}");
        }

    }
}