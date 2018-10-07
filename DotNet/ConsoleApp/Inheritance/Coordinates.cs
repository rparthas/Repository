using System;
using System.Collections.Generic;
using System.Text;

namespace Inheritance
{
    public struct Coordinates
    {
        public double latitude;
        public double longitude;
        public override string ToString() => $"{this.latitude}--{this.longitude}";
    }

    
}
