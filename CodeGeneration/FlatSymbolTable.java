package CodeGeneration;
import AdaSemantic.*;
import java.util.HashMap;
import java.util.Map;
public class FlatSymbolTable{
	HashMap<String, AdaSymbol> table;
	public FlatSymbolTable(){
		this.table=new HashMap<String, AdaSymbol>();
	}
	/**Hace un recorrido en profundidad de la tabla para aplanarla.*/
	public FlatSymbolTable(LinkedSymbolTable table){
		this.table=new HashMap<String, AdaSymbol>();
		pre_order_traverse(table);
	}
	/**Hace un recorrido en orden previo del árbol del cual la tabla v es raíz: no meter las funciones ni records!*/
	private void pre_order_traverse(LinkedSymbolTable v){		
		//agregar los símbolos de esta tabla a la tabla:
		AdaSymbol val=null;
		for(Map.Entry entry: v.getTable().entrySet()){
			val=(AdaSymbol)entry.getValue();
			String cle=v.getFlatId()+"."+entry.getKey();
			if(!(val.type instanceof FunctionType) && !this.table.containsKey(cle))
				this.table.put(cle, (AdaSymbol)entry.getValue());
		}
		//hacer el recorrido en preorden de los hijos:
		for(LinkedSymbolTable child: v.offspring)
			pre_order_traverse(child);
	}

	/**Representa la tabla*/
	public String toString(){
		StringBuilder s=new StringBuilder();
		for(Map.Entry entry: table.entrySet()){
			s.append(String.format("%s\t|\t{%s}\n", entry.getKey(), entry.getValue()));
		}
		return s.toString();
	}

	/**Para obtener un valor: buscar en currentScope, y de ahí, en sus ancestros.*/
	public SymbolLookup get(String currentScope, String key){
		if (currentScope.isEmpty())
			return new SymbolLookup(0, this.table.get(key));
		String[] tokenized=currentScope.split("__");
		StringBuilder finder=new StringBuilder(currentScope);
		String lookup=currentScope;
		AdaSymbol found=new AdaSymbol();
		int q_depth=0;
		String l_key=lookup+"."+key;
		//ir en reversa
		for(int i=tokenized.length-1; i>=0; i--){
			if ((found=this.table.get(l_key)) != null)
				break;
			q_depth=tokenized.length-i;
			lookup=(i==0)? tokenized[i] : finder.substring(0, finder.lastIndexOf("__"+tokenized[i]));
			l_key=lookup+"."+key;
		}

		//en base a la profundidad de éste y del llamado, devolver el número de saltos:
		int saltos=q_depth;
		return new SymbolLookup(q_depth, found);	
	}
	
	/**Para poner algo en esta tabla*/
	public void put(String key, AdaSymbol sym){
		this.table.put(key, sym);
	}
	/**Para obtener la dirección de un símbolo*/
}
