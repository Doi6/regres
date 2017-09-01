package doi.regres.util;

import java.util.Iterator;

import doi.regres.Num;

public class Matrix 
   implements Iterable<Num>
{
   /// number of rows
   protected int rows;
   /// number of cols
   protected int cols;
   /// the values in matrix
   protected Num [] items;
   
   public Matrix( int rows, int cols ) {
      this.rows = rows;
      this.cols = cols;
      items = new Num [rows*cols];
   }

   /// get a value in matrix
   public Num get( int r, int c ) {
      return items[ r*cols + c ];
   }
   
   /// set a value in matrix
   public void set( int r, int c, Num val ) {
      items[ r*cols + c ] = val;
   }
   
   /// create a clone of matrix
   public Matrix clone() {
      Matrix ret = new Matrix( rows, cols );
      for (int i=0; i < rows*cols; ++i)
         ret.items[i] = items[i];
      return ret;
   }
   
   /// dimensions of matrix
   public String dim() { return ""+rows+":"+cols; }
   
   /// a row
   public Vect row( int r ) {
      return new Line( cols, r*cols, 1 );  
   }
   
   
   /// solve matrix equation this * x = b
   public Matrix solve( Matrix b ) {
      Matrix ret = b.clone();
      clone().eliminate( ret );
      return ret;
   }

   /// iterate through elements
   public Iterator<Num> iterator() {
      return new Line( rows*cols, 0, 1 ).iterator();
   }
   
   /// eliminate this matrix to identity
   /// changing b to the solution of this * x = b
   public void eliminate( Matrix b ) {
      if ( rows != cols )
         throw exc( "Can only eliminate in square matrix");
      if ( rows != b.rows )
         throw exc( "Row counts do not match: "+rows+" != "+b.rows );
      for (int i=0; i < rows; ++i)
         eliminateCol( i, b );
   }
   
   /// dump matrix
   public String toString() {
      StringBuilder ret = new StringBuilder();
      for (int r=0; r < rows; ++r)
         ret.append( ""+row(r)+"\n" );
      return ret.toString();
   }
   
   /// change col c in matrix to have 1 in cth value and 0 in others
   /// change b to keep fulfilling equation
   protected void eliminateCol( int c, Matrix b ) {
// System.err.println(""+this);      
// System.err.println(""+b);      
      swapNonzero( c, b );
      divRow( c, b );
      for (int i=0; i < rows; ++i) {
         if (c != i)
            subScaledRow( c, i, b );
      }
// System.err.println("eliminatecol"+c+"\n");      
   }
   
   /// swap a row to cth which has nonzero cth item
   /// do same swap in b
   protected void swapNonzero( int c, Matrix b  ) {
      Num z = get(c,c).zero();
      for (int j=c; j<rows; ++j) {
         if ( ! z.equals(get(j,c)) ) {
// System.err.println("swap "+j+" "+c+"\n");      
            swapRows( c, j );
            b.swapRows( c, j );
            return;
         }
      }
      throw exc("Cannot eliminate zero column: "+c);
   }
   
   /// swap rows i and j 
   protected void swapRows( int i, int j ) {
      if (i == j)
         return;
      for (int c=0; c<cols; ++c) {
         Num x = get(i,c);
         set(i,c,get(j,c));
         set(j,c,x);
      }
   }
   
   /// divide ith row in this and b with [i,i]
   protected void divRow( int i, Matrix b ) {
      Num x = get(i,i);
      row( i ).setdiv( x );
      b.row( i ).setdiv( x );
   }
   
   /// subtract row(c) * [r,c] from row(r)
   /// subtract b.row(c) * [r,c] from b.row(r) 
   protected void subScaledRow( int c, int r, Matrix b ) {
      Num x = get(r,c);
      row(r).setminusmul( row(c), x );
      b.row(r).setminusmul( b.row(c), x );
   }
   
   
   /// a new exception with message
   protected MatrixException exc( String msg ) {
      return new MatrixException(msg);
   }
   
   /// exception in matrix operations
   public static class MatrixException
      extends RuntimeException
   {
      public MatrixException(String msg) { super(msg); }
   }

   /// row or column of matrix
   protected class Line
      extends Vect
      implements Iterable<Num>
   {
      /// size of row or column 
      protected int size;
      /// index of first element in items 
      protected int start;
      /// stride between items
      protected int step;
      
      public Line(int size, int start, int step) {
         this.size = size;
         this.start = start;
         this.step = step;
      }
      
      public int size() { return size; }
      
      public Num get(int i) {
         return items[start+i*step];
      }
      
      public void set( int i, Num x ) {
         items[start+i*step] = x;
      }
      
      protected String stringSeps( int kind ) {
         return 1 == kind ? "\t": "";
      }
      
      public Iterator<Num> iterator() { return new Iter(); }
      
      public class Iter
         implements Iterator<Num>
      {
         int at=0;
         public boolean hasNext() { return at < size; }
         public Num next() { return get(at++); }
         
      }
      
   }      
   
   
   
   
}
