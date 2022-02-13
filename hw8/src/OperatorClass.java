/**
 *  Operator interface
 */
interface Operator{
    double getValue(double a, double b);
}

class Multiplication implements Operator{
    /** Combines an edge weight with a path weight with multiplication
     * @param a edge weight
     * @param b path weight
     * @return result
     */
    public double getValue(double a, double b) {
      return a * b;
    }
}
  
class Addition implements Operator{
    /** Combines an edge weight with a path weight with addition
     * @param a edge weight
     * @param b path weight
     * @return result
     */
    public double getValue(double a, double b) {
      return a + b;
    }
}
  
class Subtraction implements Operator{
    /** Combines an edge weight with a path weight with subtraction
     * @param a edge weight
     * @param b path weight
     * @return result
     */
    public double getValue(double a, double b) {
      return a - b;
    }
}

// operator class, responsible to return the appropriate operator class
public class OperatorClass{

    /** Return wanted operator
     * @param type operator
     * @return
     */
    public Operator createOperator(String type) {
        if (type == null || type.isEmpty())
            return null;
        if ("addition".equals(type)) {
            return new Addition();
        }
        else if ("subtraction".equals(type)) {
            return new Subtraction();
        } else if ("multiplication".equals(type)) {
            return new Multiplication();
        }
        return null;
    }
}