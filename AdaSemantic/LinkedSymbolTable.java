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
		table=new HashMap<String, Type>();
		this.ancestor= ancestor;
	}
	
	public LinkedSymbolTable(){
		table=new HashMap<String, Type>();
		this.ancestor=null;	
	}
	
	public LinkedSymbolTable getAncestor(){
		return this.ancestor;
	}
	
	public HashMap<String, Type> getTable(){
		return this.table;
	}	

	public boolean put(String id, Type tipo){
		//because of the case insensitiveness of ada:
		id=id.toLowerCase();
		if(!(this.table.containsKey(id))){
			table.put(id, tipo);
			return true;
		}else{//shouldn't let it overwrite the existing key!
			return false;
		}
	}
	
	/**Searches iteratively up the linked list of scopes the required identifier 
	   cf. The book by Aho, Lam; pp 88-89.	
	   @param id the id to search for	
	*/
	public Type get(String id){
		//because of ada's case insensitiveness:
		id=id.toLowerCase();
		for(LinkedSymbolTable t=this; t != null; t= t.getAncestor()){
			Type found=t.getTable().get(id);
			if(found != null)
				return found;
		}
		return null;
	}	
	
}
