package AdaSemantic;
import java.util.ArrayList;
/**Representa una lista de saltos para backtracking.*/ 
public class ListaSalto{
	public ArrayList<Integer> lista;
	public ListaSalto(){
		this.lista=new ArrayList<Integer>();
	}
	public ListaSalto(int index){
		lista=new ArrayList<Integer>();
		lista.add(new Integer(index));
	}
	/**Copia los valores de la otra lista a la presente. Idealmente, nada debería sobreescrirse!*/
	public static ListaSalto fusiona(ListaSalto another, ListaSalto other){
		ListaSalto rv=new ListaSalto();
		rv.lista.addAll(another.lista);		
		rv.lista.addAll(other.lista);
		return rv;
	}
		
}
