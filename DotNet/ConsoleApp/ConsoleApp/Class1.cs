using System;
namespace ConsoleApp
{
    internal class Class1
    {
        public Class1()
        {

        }

        public int IntProperty
        {
            get {
                return IntProperty > 0 ? IntProperty : -1;
            }

            set
            {
                IntProperty = value+1;
            }
        }
        public string myProperty { get; set; }

        public void method1(int parameter1,string parameter2)
        {
            Console.WriteLine("Parameter1:{0} ,Parameter 2: {1}",parameter1,parameter2);
        }
    }
}