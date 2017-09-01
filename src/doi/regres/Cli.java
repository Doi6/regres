package doi.regres;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import doi.regres.func.Linear;
import doi.regres.func.Polynomial;
import doi.regres.num.DoubleNum;
import doi.regres.num.FloatNum;
import doi.regres.num.Rational;

public class Cli {

   public static final String
      DOUBLE = "double",
      FLOAT  = "float",
      LIN    = "lin",
      POL    = "pol",
      RATIONAL = "rational";
   
   /// base data type
   protected Num num;
   /// functions to try
   protected List<Function> funcs;
   /// input data 
   protected Datas input;
   /// output data
   protected Datas output;
   
   /// run command line regression function
   public static void main( String [] args ) {
      Cli cli = new Cli();
      cli.run( args );
   }

   public Cli() {
      funcs = new LinkedList<Function>();
   }
   
   /// get cli arguments and run program
   /// show usage if wrong arguments
   public void run(String [] args) {
      try {
         getArgs( args );
         run();
      } catch (ArgException ae) {
         usage();
         System.err.println( ae.getMessage() );
      }
   }
   
   /// run regression
   protected void run() {
      Results best = null;
      for (Function f:funcs) {
         Results r = new Results( f );
         best = better( best, r );
         System.out.println( r.dump() );
      }
      System.out.println("Best match:");
      System.err.println( best.dump() );
   }
   
   protected void usage() {
      PrintStream pw = System.err;
      pw.println("Usage: java "+getClass().getName()
         +" [-t <type>] [-f <functions>] -i <infile> -o <outfile>");
      pw.println("Types:");
      pw.println("   float - 32bit floating point number");
      pw.println("   double - 64bit floating point number");
      pw.println("   rational - 64bit/64bit rational number");
      pw.println("Functions:");
      pw.println("   lin - linear - y = a*x + b");
      pw.println("   pol<n> - n degree polynomial");
      pw.println("      e.g pol3: y = a*x^3 + b*x^2 + c*x + d");
   }
   
   /// get all arguments
   protected void getArgs( String [] args ) {
      for ( int i = 0; i < args.length; )
         i = getArg( args, i );
      if (funcs.isEmpty()) {
         function(LIN);
         function(POL+2);
         function(POL+3);
      }
      if (null == input)
         throw new ArgException("input data file not set");
      if (null == output)
         throw new ArgException("output data file not set");
      int is = input.size();
      int os = output.size();
      if (is != os)
         throw new ArgException("Input and output size mismatch: "+is+" != "+os );
   }
   
   /// get one argument
   protected int getArg( String [] args, int at ) {
      try {
         String arg = args[at++];
         if ("-t".equals(arg))
            type( args[at++] );
         else if ("-f".equals(arg))
            function( args[at++] );
         else if ("-i".equals(arg))
            input = read( args[at++] );
         else if ("-o".equals(arg))
            output = read( args[at++] );
         else
            throw new ArgException("Unkonwn arg: "+arg);
         return at;
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new ArgException("argument expected");
      }
   }
   
   /// set numeric type
   protected void type( String value ) {
      String v = value.trim().toLowerCase();
      if (DOUBLE.equals(v))
         num = new DoubleNum(0d);
      else if (FLOAT.equals(v))
         num = new FloatNum(0f);
      else if (RATIONAL.equals(v))
         num = new Rational(0,1);
      else
         throw new ArgException("Unknown type: "+value);
   }
   
   /// set used functions by name 
   /// or comma-separated list of names
   protected void function( String value ) {
      int i = value.indexOf(',');
      if (0 <= i) {
         function( value.substring(0,i) );
         function( value.substring(i+1) );
      } else {
         String v = value.trim().toLowerCase();
         Function f;
         if ("lin".equals(v)) {
            f = new Linear();
         } else if (v.startsWith("pol")) {
            int n = integer( v.substring(3) );
            f = new Polynomial(n);
         } else
            throw new ArgException("Unkonwn function: "+v);
         funcs.add(f);
      }
   }
   
   /// get an integer argument
   protected int integer( String arg ) {
      try {
         return Integer.parseInt(arg);
      } catch (NumberFormatException e) {
         throw new ArgException("Unknown integer: "+arg);
      }
   }
   
   /// read numeric values from file
   protected Datas read( String filename ) {
      try {
         Datas ret = new Datas();
         BufferedReader br = new BufferedReader(
            new FileReader( filename ));
         String line;
         while (null != (line = br.readLine())) {
            if ( ! line.isEmpty())
               ret.add( num().fromString(line) );
         }
         br.close();
         return ret;
      } catch (IOException e) {
         throw new RegresException("input error: "+e.getMessage(), e );
      }
   }
   
   protected Num num() {
      if (null == num)
         num = new FloatNum(0f);
      return num;
   }
   
   protected Results better( Results old, Results curr ) {
      if (null == old || curr.error.lessThan(old.error) )
         return curr;
      return old;
   }
   
   public static class ArgException
      extends RuntimeException
   {
      public ArgException(String msg) { super(msg); }
   }
   
   /// results of an approximation
   public class Results {
      Function func;
      Params pars;
      Num error;
      
      Results( Function func ) {
         this.func = func;
         pars = func.approx( input, output );
         error = func.error( pars, input, output );
      }
      /// string form of results
      String dump() { 
         return func.name()+" E:"+error+": "+func.equation(pars); 
       }
   }
   
}
