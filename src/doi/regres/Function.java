package doi.regres;

/// a function to use for approximation
public interface Function {

   /// name of the function
   String name();
   
   /// do the regression and return best parameters
   Params approx( Datas din, Datas dout );
   
   /// calculate squared sum of errors
   Num error( Params params, Datas din, Datas dout );
 
   /// calculate function value with params at a coordinate
   Num value( Params params, Num x );
   
   /// equation of function
   String equation(Params params);
   
}
