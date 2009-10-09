package AdaSemantic;
import java.util.HashMap;
/**
	A class to represent a symbol table for a scope
	@author Luis Felipe Borjas
	@date 09 Oct 09
*/

public class LinkedSymbolTable{
	/**The Symbol table for this scope*/
	private HashMap<String, Type> table;
	/**The inmedeate outer scope*/
	private LinkedSymbolTable ancestor;
	
	public LinkedSymbolTable(LinkedSymbolTable ancestor){
		this.ancestor= ancestor;
	}
	
	public LinkedSymbolTable getAncestor(){
		return this.ancestor;
	}
	
	public HashMap<String, Type> getTable(){
		return this.table;
	}	

	public void put(String id, Type tipo){
		table.put(id, tipo);
	}
	
	/**Searches iteratively up the linked list of scopes the required identifier 
	   cf. The book by Aho, Lam; pp 88-89.	
	   @param id the id to search for	
	*/
	public Type get(String id){
		for(LinkedSymbolTable t=this; t != null; t= t.getAncestor()){
			Type found=t.getTable().get(id);
			if(found != null)
				return found;
		}
		return null;
	}	
	
}
