import AdaSemantic.*;
public class compareTest{
	private static void compareTypes(Type a, Type b){
		String m=(a.equals(b)) ? " " : " NO ";
			System.out.println("El tipo "+a.toString()+m+"es igual al tipo "+b.toString());
	}
	public static void main(String[] args){
		Type in=new IntegerType();
		Type real=new FloatType();
		Type boo=new BooleanType();
		Type str=new StringType(2);
		
		Type in1=new IntegerType("entero");
		Type real1=new FloatType("real");
		Type boo1=new BooleanType("booleano");
		
		Type in2=new IntegerType("INTEGER");
		Type real2=new FloatType("FLOAT");
		Type boo2=new BooleanType("BOOLEAN");
		
		Type in3=new IntegerType();
		Type real3=new FloatType();
		Type boo3=new BooleanType();

		//records:
		 LinkedSymbolTable depth3=new LinkedSymbolTable();
                LinkedSymbolTable depth2=new LinkedSymbolTable();
                LinkedSymbolTable depth1=new LinkedSymbolTable();
                depth3.put("record", new AdaSymbol(new FloatType()));
                depth2.put("a", new AdaSymbol(new RecordType("depth2", depth3)));
                depth2.put("int", new AdaSymbol(new IntegerType()));
                depth1.put("is", new AdaSymbol(new RecordType("depth1", depth2)));
                //put the record in a table:
                RecordType rec1=new RecordType("depth0",depth1);
		
		RecordType rec2=new RecordType("lalo", depth1);
		compareTypes(in, in1);	
		compareTypes(in, in2);
		compareTypes(in, in3);
		compareTypes(real, real1);
		compareTypes(real, real2);
		compareTypes(real, real3);
		compareTypes(boo, boo1);
		compareTypes(boo, boo2);		
		compareTypes(boo, boo3);
		compareTypes(rec1, rec2);
	}
}
