namespace ConsoleApp1 {
    public class Student : Person {

        public enum UniversityTier
        {
            Tier1,
            Tier2,
            Tier3
        }

        private UniversityTier _university;
        private double _grade;
        public Student (string name, int age, UniversityTier university, double grade, IRun runner, decimal salary = 2500) : base (name, age, runner, salary) {
            _university = university;
            Grade = grade;
        }

        public double Grade { get => _grade; set => _grade = value; }
        public UniversityTier University { get => _university; set => _university = value; }

        override public void printDetails () {
            base.printDetails ();
            System.Console.WriteLine ($"Also I have  {Grade} in {University.ToString()}");
        }

    }
}