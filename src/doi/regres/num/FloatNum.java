package doi.regres.num;

import doi.regres.Num;

public class FloatNum 
   implements Num
{
   public static float epsilon = 1e-10f;
   
   float val;
   
   public FloatNum( float f ) {
      val = f;
   }
   
   public FloatNum( String s ) {
      this( Float.parseFloat(s) );
   }
   
   public FloatNum zero() {
      return new FloatNum(0f);
   }
   
   public FloatNum one() {
      return new FloatNum(1f);
   }
   
   public FloatNum plus( Num other ) {
      return new FloatNum( val + val(other) );
   }

   public FloatNum minus( Num other ) {
      return new FloatNum( val - val(other) );
   }

   public FloatNum mul( Num other ) {
      return new FloatNum( val * val(other) );
   }

   public FloatNum div( Num other ) {
      return new FloatNum( val / val(other) );
   }
   
   public boolean lessThan( Num other ) {
      return val < val(other);
   }
   
   public FloatNum fromString( String s ) {
      return new FloatNum( s );
   }
   
   public boolean equals( Object other ) {
      return epsilon > Math.abs( val - val(other));
   }
   
   public String toString() { 
      if (val == (long)val)
         return String.format("%d", (long)val);
      return String.format("%.8s",val); 
   }

   protected float val( Object other ) { return ((FloatNum)other).val; }
   
}
