package doi.regres.num;

import doi.regres.Num;

public class DoubleNum 
   implements Num
{
   public static double epsilon = 1e-20d;

   protected double val;
   
   public DoubleNum( double val ) {
      this.val = val;
   }
   
   public DoubleNum( String s ) {
      val = Double.parseDouble(s);
   }
   
   public DoubleNum plus( Num other ) {
      return new DoubleNum( val + val(other) );
   }
   
   public DoubleNum minus( Num other ) {
      return new DoubleNum( val - val(other) ); 
   }
   
   public DoubleNum mul( Num other ) {
      return new DoubleNum( val * val(other) );
   }
   
   public DoubleNum zero() { return new DoubleNum(0d); }

   public DoubleNum one() { return new DoubleNum(1d); }
   
   public DoubleNum div( Num other ) {
      return new DoubleNum( val / val(other) );
   }

   public DoubleNum fromString( String s ) {
      return new DoubleNum(s);
   }
   
   public boolean lessThan( Num other ) {
      return val < val(other);
   }

   public boolean equals( Object other ) {
//      return val == ((FloatNum)other).val;
      return epsilon > Math.abs( val - val(other));
   }
   
   public String toString() { 
      if (val == (long)val)
         return String.format("%d", (long)val);
      return String.format("%.8s",val); 
   }
   
   protected double val( Object other ) {
      return ((DoubleNum)other).val;
   }
   
}
