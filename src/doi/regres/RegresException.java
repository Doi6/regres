package doi.regres;

/// exception during regression process
public class RegresException 
   extends RuntimeException
{
   public RegresException(String msg, Throwable t) { super(msg,t); }
}
