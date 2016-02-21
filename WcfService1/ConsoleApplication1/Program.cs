using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ServiceModel;

namespace ConsoleApplication1
{
    class Program
    {
        static void Main(string[] args)
        {
            ServiceReference1.CalculatorClient svr = new ServiceReference1.CalculatorClient();
            double sum=svr.AddNumbers(10.25, 100.25);
        }
    }
}
