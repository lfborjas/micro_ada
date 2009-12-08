package AdaSemantic;
import java.util.HashMap;
import java.util.ArrayList;
/**Clase para el resultado del front end:
la lista de cuádruplos y el mapa de tablas de símbolos*/
public class FrontEndResult{
	public ArrayList<Cuadruplo> icode;
	public HashMap<String, LinkedSymbolTable> stables;
	
	public FrontEndResult(ArrayList<Cuadruplo> i, HashMap<String, LinkedSymbolTable> st){
		icode=i;
		stables=st;
	}
}
