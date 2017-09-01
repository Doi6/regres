package doi.regres.func;

import doi.regres.Datas;
import doi.regres.Num;
import doi.regres.Params;
import doi.regres.util.Matrix;

public class Polynomial 
   extends BaseFunc
{

   /// degree of polynomial
   protected int degree;
   
   public Polynomial(int degree) {
      this.degree = degree;
   }
 
   public String name() { return "Polynomial ("+degree+")"; }
   
   public Params approx( Datas din, Datas dout ) {
      Matrix p = powers( din );
      Matrix m = matrix( p );
      Matrix y = vector( din, dout );
      m.eliminate( y );
      return new Params( y );
   }
   
   /// value of polynomial
   public Num value( Params pars, Num x ) {
      Num ret = x.zero();
      Num xx = x.one();
      for (int i=0; i<=degree; ++i) {
         Num a = pars.get(i);
         ret = ret.plus( xx.mul(a) );
         xx = xx.mul(x);
      }
      return ret;
   }
   
   /// build row vector of sum(xi^k)
   protected Matrix powers( Datas din ) {
      int n = 2*degree+1;
      Matrix ret = new Matrix( 1, n );
      for (int i=0; i < n; ++i)
         ret.set( 0, i, powersum( din, i ) );
      return ret;
   }

   /// build column vector of sum(yi*xi^k) 
   protected Matrix vector( Datas din, Datas dout ) {
      int n = degree+1;
      Matrix ret = new Matrix( n, 1 );
      for (int i=0; i<n; ++i) {
         Num x = powersum(din,i,dout);
         ret.set( i, 0, x );
      }
      return ret;
   }
   
   /// sum of (xi^k)
   protected Num powersum( Datas din, int k ) {
      Num ret = din.zero();
      for (Num xi: din)
         ret = ret.plus( power(xi,k) );
      return ret;
   }
   
   /// sum of (xi^k * yi)
   protected Num powersum( Datas din, int k, Datas dout ) {
      Num ret = din.zero();
      int n = din.size();
      for (int i=0; i<n; ++i) {
         Num x = power( din.get(i), k );
         ret = ret.plus( x.mul( dout.get(i)) );
      }
      return ret;
   }
   
   /// x^k
   protected Num power( Num x, int k ) {
      if (0 == k)
         return x.one();
      if (1 == k)
         return x;
      return x.mul( power(x,k-1) );
   }
   
   /// build matrix of sum(xi^k)-s
   protected Matrix matrix( Matrix pows ) {
      int n = degree+1;
      Matrix ret = new Matrix( n, n );
      for (int r=0; r < n; ++r) {
         for (int c=0; c < n; ++c)
            ret.set( r, c, pows.get(0,r+c) );
      }
      return ret;
   }
   
   
}
