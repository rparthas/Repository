namespace ConsoleApp1 {
    public class SlowRunner : IRun {
        public void Run (int distance) {
            System.Console.WriteLine ($"I run slowly for {distance} kms");
        }
    }
}