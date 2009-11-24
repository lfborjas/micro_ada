package AdaSemantic;
class BackPatchResult{
	public ListaSalto siguiente;
	public ListaSalto verdadera;
	public ListaSalto falsa;

	public BackPatchResult(){
		siguiente=null;
		verdadera=null;
		falsa=null;
	}

	public BackPatchResult(ListaSalto siguiente){
		this.siguiente=siguiente;
		this.verdadera=null;
		this.falsa=null;
	}

	public BackPatchResult(ListaSalto verdadera, ListaSalto falsa){
		this.siguiente=null;
		this.verdadera=verdadera;
		this.falsa=falsa;
	}
}
