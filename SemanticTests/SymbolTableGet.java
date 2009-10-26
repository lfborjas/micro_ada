import AdaSemantic.*;

public class SymbolTableGet{
	public static void testGet(LinkedSymbolTable table, Object query){
		AdaSymbol found=table.get(query);
		query=(String)query;
		if(found != null)
			System.out.println("Found "+query+": "+found.toString());
		else
			System.out.println("Symbol "+query+" not found in current scope");
	}
		
	public static void main(String[] args){
		LinkedSymbolTable dad=new LinkedSymbolTable();
		//a son:
		LinkedSymbolTable son=new LinkedSymbolTable(dad);
		//a grandson:
		LinkedSymbolTable grandson=new LinkedSymbolTable(son);
		
		//Symbol tables for records:
		LinkedSymbolTable depth3=new LinkedSymbolTable();
		LinkedSymbolTable depth2=new LinkedSymbolTable();
		LinkedSymbolTable depth1=new LinkedSymbolTable();		
		depth3.put("record", new AdaSymbol(new FloatType()));
		depth2.put("a", new AdaSymbol(new RecordType("depth2", depth3)));
		depth2.put("int", new AdaSymbol(new IntegerType()));
		depth1.put("is", new AdaSymbol(new RecordType("depth1", depth2)));
		//put the record in a table:
		son.put("this", new AdaSymbol(new RecordType("depth0",depth1)));
		//other objects:
		dad.put("test1", new AdaSymbol(new IntegerType()));
		son.put("test2", new AdaSymbol(new FloatType()));
		grandson.put("test3", new AdaSymbol(new BooleanType()));
		
		//All queries performed in the grandson:
		testGet(grandson, "test1");
		testGet(grandson, "test2");
		testGet(grandson, "test3");
		testGet(grandson, "test4");
		//record queries:
		testGet(grandson, "test3.length");	
		testGet(grandson, "this");
		testGet(grandson, "this.is.int");
		testGet(grandson, "this.is.int.MAX_INT");
		testGet(grandson, "this.is.lalo");
		testGet(grandson, "this.is.a");
		testGet(grandson, "THIS.IS.A.RECORD");
		
	
	}
}
