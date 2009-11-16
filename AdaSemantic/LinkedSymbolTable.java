package AdaSemantic;
import java.util.HashMap;
/**
	A class to represent a symbol table for a scope
	@author Luis Felipe Borjas
	@date 09 Oct 09
*/

public class LinkedSymbolTable{
	/**The Symbol table for this scope*/
	private HashMap<String, AdaSymbol> table;
	/**The immediate outer scope*/
	private LinkedSymbolTable ancestor;
	/**La primera dirección relativa libre*/
	public int desplazamiento;
	
	public LinkedSymbolTable(LinkedSymbolTable ancestor){
		table=new HashMap<String, AdaSymbol>();
		this.ancestor= ancestor;
		this.desplazamiento=0;
	}
	
	public LinkedSymbolTable(){
		table=new HashMap<String, AdaSymbol>();
		this.ancestor=null;
		this.desplazamiento=0;	
	}
	
	public LinkedSymbolTable getAncestor(){
		return this.ancestor;
	}
	
	public HashMap<String, AdaSymbol> getTable(){
		return this.table;
	}	

	public boolean put(Object oid, AdaSymbol tipo){
		String id=(String)oid;
		//because of the case insensitiveness of ada:
		id=id.toLowerCase();
		if(!(this.table.containsKey(id))){
			//dar la dirección de memoria:
			tipo.address=this.desplazamiento;
			//actualizar el desplazamiento:
			desplazamiento += tipo.type.width;
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
	public AdaSymbol get(Object oid){
		String id=(String)oid;
		//because of ada's case insensitiveness:
		id=id.toLowerCase();
		AdaSymbol found=new AdaSymbol();
		//for records:
		String [] splitId=id.split("\\.");
		for(LinkedSymbolTable t=this; t != null; t= t.getAncestor()){
			found=t.getTable().get(splitId[0]);
			if(found != null){
				if(splitId.length == 1)//is not a selected component query					
					return found;
				else//it IS a selected component query
					break;
			}
		}
		//the old one: if it's NOT a selected component query NOR was the id found:
		if (found == null)	
			return null;
		//as it is a selected component query, then the type of this found object MUST be selectable (i.e. Record)			
		if(!(found.type instanceof RecordType))
			return null;					
		//it is a record, so, try to fetch it's next components (>splitId[1])		
		RecordType f;
		for(int i=1; i<splitId.length-1; i++){
			f=(RecordType)found.type;		
			found=f.symbolTable.getTable().get(splitId[i]);
			if(found != null){
				if(!(found.type instanceof RecordType))
					return null;				
			}else{
				return null;
			}
			
		}
		//got to the end, try to fetch the last one (which may be null):
		f=(RecordType)found.type;
		found=f.symbolTable.getTable().get(splitId[splitId.length-1]);
		return found;
	}	
	
}
