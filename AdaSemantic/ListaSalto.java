package AdaSemantic;
import java.util.Hashtable;
/**Representa una lista de saltos para backtracking. Las llaves son los cuádruplos (el índice en la lista de cuádruplos)
donde están los saltos y los valores son los saltos para esos cuádruplos.*/
public class ListaSalto{
	public Hashtable<Integer, String> lista;

	public ListaSalto(int index){
		lista=new Hashtable<Integer, String>();
		lista.put(new Integer(index), "");
	}
	/**Copia los valores de la otra lista a la presente. Idealmente, nada debería sobreescrirse!*/
	public void fusiona(Hashtable<Integer, String> other){
		this.lista.putAll(other);
	}
	
	/**Le pone el salto a todas las entradas de la lista*/
	public void completa(String salto){
		for(Integer index: lista.keySet()){
			lista.put(index, salto);
		}

	}
}
