package doi.regres.func;

import doi.regres.Datas;
import doi.regres.Function;
import doi.regres.Num;
import doi.regres.Params;

public abstract class BaseFunc 
   implements Function
{

   /// calculate sum of squared errors
   public Num error( Params params, Datas din, Datas dout ) {
      Num ret = din.zero();
      int n = din.size();
      for (int i=0; i < n; ++i) {
         Num x = din.get(i);
         Num error = dout.get(i).minus( value(params, x) );
         ret = ret.plus( error.mul(error) );
      }
      return ret;   
   }
}
