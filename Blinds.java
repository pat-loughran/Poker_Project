

public enum Blinds {
  
  ONETWO(new Integer[] {1,2}),
  TWOFIVE(new Integer[] {2,5}),
  FIVETEN(new Integer[] {5,10}),
  ;
  
  private final Integer[] blindValues;
  
   Blinds(Integer[] blindValues) {
    this.blindValues = blindValues;
  }
   public Integer[] getBlindValues() {
     return this.blindValues;
   }
   
   public int getBigBlind() {
	   int blind = this.ordinal();
	   switch(blind) {
	   case 0: 
		   return 2;
	   case 1:
		   return 5;
	   case 2:
		   return 10;
	   }
		   return 0;
	   
   }
}
