package AdaSemantic;
public class BackPatchResult{
	public ListaSalto siguiente;
	public ListaSalto verdadera;
	public ListaSalto falsa;

	public BackPatchResult(){
		siguiente=new ListaSalto();
		verdadera=new ListaSalto();
		falsa=new ListaSalto();
	}

	public BackPatchResult(ListaSalto siguiente){
		this.siguiente=siguiente;
		this.verdadera=new ListaSalto();
		this.falsa=new ListaSalto();
	}

	public BackPatchResult(ListaSalto verdadera, ListaSalto falsa){
		this.siguiente=new ListaSalto();
		this.verdadera=verdadera;
		this.falsa=falsa;
	}
	
	public BackPatchResult(ListaSalto siguiente, ListaSalto verdadera, ListaSalto falsa){
		this.siguiente=siguiente;
		this.verdadera=verdadera;
		this.falsa=falsa;
	}
}
