package doi.regres;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/// function parameters
public class Params {

   /// the best parameters
   protected List<Num> params;

   /// create empty params
   public Params( int size ) {
      params = new ArrayList<Num>(size);
      for (;0<size;--size)
         params.add(null);
   }

   /// create params by iterable
   public Params( Iterable<Num> source ) {
      List<Num> l = new LinkedList<Num>();
      for (Num n: source)
         l.add(n);
      params = new ArrayList<Num>(l);
   }
   
   public Num get( int i ) {
      return params.get(i);
   }
   
   public void set( int i, Num value ) {
      params.set(i, value);
   }
   
   public String dump() {
      StringBuilder ret = new StringBuilder();
      boolean first = true;
      for (Num n: params) {
         if (first)
            first = false;
            else ret.append(" "); 
         ret.append(n.toString());
      }
      return ret.toString();
   }
   
}
