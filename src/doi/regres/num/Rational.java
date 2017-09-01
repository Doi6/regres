package doi.regres.num;

import doi.regres.Num;

public class Rational 
   implements Num
{

   /// denominator
   protected long den;
   /// divisor
   protected long div;
   
   public Rational( long den, long div ) {
      this.den = den;
      this.div = div;
   }
   
   public Rational simplify() {
      if (0 == den) {
         div=1;
      } else if (0 == div) {
         div /= Math.abs(div);
      } else {
         long gcd = gcd( Math.abs(den), Math.abs(div));
         den /= gcd;
         div /= gcd;
         if (0 > div) {
            den = -den;
            div = -div;
         }
      }
      return this;
   }
   
   public Rational div( Num other ) {
      Rational o = (Rational)other;
      return new Rational( den * o.div, div * o.den ).simplify();
   }
   
   public Rational fromString( String s ) {
      int i = s.indexOf('/');
      long den, div;
      if ( 0 <= i ) {
         den = aslong( s.substring(0,i));
         div = aslong( s.substring(i+1));
      } else {
         den = aslong(s);
         div = 1;
      }
      return new Rational(den, div).simplify();
   }
   
   public boolean negative() {
      return 0 > den * div;
   }
   
   public Rational plus( Num other ) {
      Rational o = (Rational)other;
      return new Rational( den*o.div + o.den*div, div*o.div).simplify();
   }

   public Rational minus( Num other ) {
      Rational o = (Rational)other;
      return new Rational( den*o.div - o.den*div, div*o.div).simplify();
   }

   public Rational mul( Num other ) {
      Rational o = (Rational)other;
      return new Rational( den*o.den, div*o.div ).simplify();
   }
   
   public Rational zero() {
      return new Rational(0,1);
   }
   
   public Rational one() {
      return new Rational(1,1);
   }
   
   public boolean lessThan( Num other ) {
      return minus( other ).negative(); 
   }

   public String toString() {
      if ( 1 == div )
         return ""+den;
      return ""+den+"/"+div;
   }
   
   protected long aslong( String s ) {
      return Long.parseLong(s);
   }
   
   /// gcd of two positive numbers
   protected long gcd( long a, long b ) {
      if ( a == b | 0 == b)
         return a;
      if (b < a)
         return gcd( b, a % b );
      return gcd( a, b % a );
   }
}
