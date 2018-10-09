using System;
using System.IO;

namespace File
{
    class Program
    {
        static void Main(string[] args)
        {
            string path = "Z:\\git\\repository\\DotNet\\ConsoleApp\\File\\";
            FileSync(path);
            FileAsync(path);
            Console.ReadKey();
        }

        private static void FileSync(string path)
        {
            try
            {
                using (StreamReader reader = new StreamReader($"{path}/random.txt"))
                {
                    String contents = reader.ReadToEnd();
                    using (StreamWriter writer = new StreamWriter($"{path}random-copy.txt"))
                    {
                        writer.WriteLine(contents);
                    }
                }
            }
            catch (Exception exception)
            {

                Console.WriteLine($"Error ${exception}");
            }
          
        }

        private static async void FileAsync(string path)
        {
            try { 
                using (StreamReader reader = new StreamReader($"{path}/random.txt"))
                {
                    String contents = await reader.ReadLineAsync();
                    using (StreamWriter writer = new StreamWriter($"{path}random-copy.txt"))
                    {
                        while (contents != null)
                        {
                            await writer.WriteAsync(contents);
                            contents = await reader.ReadLineAsync();
                        }
                    }
                }
            }
            catch (Exception exception)
            {

                Console.WriteLine($"Error ${exception}");
            }
        }
    }
}
