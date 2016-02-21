using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace WcfService1
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "ICalculator" in both code and config file together.
    [ServiceContract]
    public interface ICalculator
    {
        [OperationContract]
        double AddNumbers(double number1, double number2);

        [OperationContract]
        double SubtractNumbers(double number1, double number2);

        [OperationContract]
        double MultiplyNumbers(double number1, double number2);
    }
}
