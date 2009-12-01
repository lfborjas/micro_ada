package AdaSemantic;
/**A class that represents a non-terminal in a syntax directed translation*/
public class ParserResult{
	/**The type of the production*/
	public Type type;
	/**Any other value useful:*/
	public Object value;
	/**A flag to indicate if a result was somehow corrupt, and hence not candidate to code generation*/
	public boolean clean;
	/**The lists for backpatching*/
	public BackPatchResult backpatch;
	/**The intermediate address for this result (only used in functions, so far)*/
    	public String place;

	public ParserResult(){
		this.type=null;
		this.value=null;
		this.clean=true;
		this.backpatch=new BackPatchResult();
		this.place=null;
	}

	public ParserResult(Type t){
		this.type=t;
		this.value=null;
		this.clean=true;
		this.place=null;
	}

	public ParserResult(Object v){
		this.value=v;
		this.type=null;
		this.clean=true;
		this.place=null;
	}

	public ParserResult(Object v, Type t){
		this.type=t;
		this.value=v;
		this.clean=true;
		this.place=null;
	}

	public ParserResult(Object v, Type t, boolean c){
		this.value=v;
		this.type=t;
		this.clean=c;
		this.place=null;	
	}

	public ParserResult(Object v, boolean c){
		this.value=v;
		this.type=null;
		this.clean=c;
		this.place=null;
	}

	public ParserResult(Type t, boolean c){
		this.type=t;
		this.value=null;
		this.clean=c;
		this.place=null;
	}

	public ParserResult(Object v, Type t, BackPatchResult b){
		this.value=v;
		this.type=t;
		this.backpatch=b;
		this.clean=true;
		this.place=null;
	}

	public ParserResult(Object v, BackPatchResult b){
		this.value=v;
		this.backpatch=b;
		this.type=null;
		this.clean=true;
		this.place=null;
	}

	public ParserResult(Type t, BackPatchResult b){
		this.type=t;
		this.value=null;
		this.clean=true;
		this.backpatch=b;
		this.place=null;
	}
	
	public ParserResult(BackPatchResult b){
		this.backpatch=b;
		this.type=null;
		this.value=null;
		this.clean=true;
		this.place=null;
	}
	
	public ParserResult(Object v, Type t, String p){
		this.value=v;
		this.type=t;
		this.place=p;
		this.clean=false;
		this.backpatch=null;
	}	
	public ParserResult(Object v, Type t, BackPatchResult b, String p){
		this.value=v;
		this.type=t;
		this.backpatch=b;
		this.place=p;
		this.clean=true;
	}
}
