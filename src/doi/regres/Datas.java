package doi.regres;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/// input or output datas for approximation
public class Datas 
   implements Iterable<Num>
{

   /// the datas themsleves
   protected List<Num> items;
   
   public Datas() {
      items = new ArrayList<Num>();
   }
   
   /// add a new data value
   public void add( Num n ) {
      items.add( n );
   }
   
   /// number of datas
   public int size() {
      return items.size();
   }
   
   /// get one data
   public Num get(int i) {
      return items.get(i);
   }
   
   /// get the constant 0 value
   public Num zero() {
      return get(0).zero();
   }

   public Iterator<Num> iterator() { return items.iterator(); }
   
}
