package doi.regres;

public interface Num {

   /// this number is less than other
   boolean lessThan( Num other );
 
   /// creates new number from string
   Num fromString(String s);
   
   /// creates new zero
   Num zero();
   
   /// creates new one
   Num one();
   
   /// add other and return sum
   Num plus( Num other );
   
   /// subtract other and return result
   Num minus( Num other );
   
   /// multiply with other and return sum
   Num mul( Num other );
   
   /// divide with other and return result
   Num div( Num other );
   
}
