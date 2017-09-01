package doi.regres.util;

import doi.regres.Num;

/// a vector of Nums
public abstract class Vect {

   /// number of items
   public abstract int size();
   /// get a value
   public abstract Num get( int i ); 
   /// set a value
   public abstract void set( int i, Num val );

   /// divide each item with n
   public void setdiv( Num x ) {
      int n = size();
      for (int i=0; i<n; ++i)
         set( i, get(i).div(x) );
   }
   
   /// subtract other * x
   public void setminusmul( Vect other, Num x ) {
      int n = size();
      for (int i=0; i<n; ++i)
         set( i, get(i).minus( other.get(i).mul(x) ) );
   }

   public String toString() {
      StringBuilder ret = new StringBuilder();
      ret.append( stringSeps(0) );
      int n = size();
      boolean first = true;
      for (int i=0; i<n; ++i) {
         if (first)
            first = false;
            ret.append( stringSeps(1) );
         ret.append( get(i).toString() );
      }
      ret.append( stringSeps(2) );
      return ret.toString();
   }
   
   /// separators used for printing
   protected String stringSeps( int kind ) {  
      switch (kind) {
         case 0: return "[";
         case 1: return ",";
         case 2: return "]";
      }
      return "";
   }
}
