package AdaSemantic;
import java.util.HashMap;
import java.util.ArrayList;
import CodeGeneration.FlatSymbolTable;
/**Clase para el resultado del front end:
la lista de cuádruplos y el mapa de tablas de símbolos*/
public class FrontEndResult{
	public ArrayList<Cuadruplo> icode;
	public FlatSymbolTable table;
	
	public FrontEndResult(ArrayList<Cuadruplo> i, FlatSymbolTable st){
		icode=i;
		table=st;
	}
}
