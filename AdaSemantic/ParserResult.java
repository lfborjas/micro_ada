package AdaSemantic;
/**A class that represents a non-terminal in a syntax directed translation*/
public class ParserResult{
	/**The type of the production*/
	public Type type;
	/**Any other value useful:*/
	public Object value;
	/**A flag to indicate if a result was somehow corrupt, and hence not candidate to code generation*/
	public boolean clean;

	public ParserResult(){}

	public ParserResult(Type t){
		this.type=t;
		this.value=null;
		this.clean=true;
	}

	public ParserResult(Object v){
		this.value=v;
		this.type=null;
		this.clean=true;
	}

	public ParserResult(Object v, Type t){
		this.type=t;
		this.value=v;
		this.clean=true;
	}

	public ParserResult(Object v, Type t, boolean c){
		this.value=v;
		this.type=t;
		this.clean=c;	
	}

	public ParserResult(Object v, boolean c){
		this.value=v;
		this.type=null;
		this.clean=c;
	}

	public ParserResult(Type t, boolean c){
		this.type=t;
		this.value=null;
		this.clean=c;
	}
	
}
