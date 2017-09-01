package doi.regres.func;

import doi.regres.Datas;
import doi.regres.Num;
import doi.regres.Params;

public class Linear
   extends BaseFunc
{

   public String name() { return "Linear"; }
   
   public Params approx( Datas din, Datas dout ) {
      Params ret = new Params(2);
      int n = din.size();
      Num a = din.zero();
      Num b = din.zero();
      Num c = din.zero();
      Num d = din.zero();
      Num nn = din.zero().fromString(""+n); 
      for (int i=0; i < n; ++i) {
         Num xi = din.get(i);
         Num yi = dout.get(i);
         a = a.plus( xi.mul( yi ) );
         b = b.plus( yi );
         c = c.plus( xi.mul( xi ) );
         d = d.plus( xi );
      }
      Num e = a.minus(b.mul(d).div(nn));
      Num f = c.minus(d.mul(d).div(nn));
      Num alpha = e.div(f);
// System.err.println("a:"+a+" b:"+b+" c:"+c+" d:"+d+" e:"+e+" f:"+f);      
      ret.set( 1, alpha );
      ret.set( 0, b.minus( alpha.mul(d)).div(nn) );
      return ret;
   }
   
   public Num value( Params params, Num x ) {
      return params.get(1).mul( x ).plus( params.get(0) ); 
   }
   
}
