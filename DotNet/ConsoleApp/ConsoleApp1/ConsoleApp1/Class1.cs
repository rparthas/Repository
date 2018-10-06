using System;
namespace ConsoleApp1
{
    internal class Class1
    {
        public Class1()
        {

        }

        private int _IntProperty;

        public int IntProperty
        {
            get => this._IntProperty > 0 ? this._IntProperty : -1;

            set => this._IntProperty = value + 1;

        }
        public string myProperty { get; set; }

        public void method1(int parameter1, string parameter2)
        {
            Console.WriteLine("Parameter1:{0} ,Parameter 2: {1}", parameter1, parameter2);
        }
    }
}