package AdaSemantic;
import java.util.ArrayList;
/**Representa una lista de saltos para backtracking.*/ 
public class ListaSalto{
	public ArrayList<Integer> lista;

	public ListaSalto(int index){
		lista=new ArrayList<Integer>();
		lista.add(new Integer(index));
	}
	/**Copia los valores de la otra lista a la presente. Idealmente, nada debería sobreescrirse!*/
	public void fusiona(ListaSalto other){
		this.lista.addAll(other.lista);
	}
		
}
