package AdaSemantic;
/**A class that represents a non-terminal in a syntax directed translation*/
public class ParserResult{
	/**The type of the production*/
	public Type type;
	/**Any other value useful:*/
	public Object value;
	
	public ParserResult(){}

	public ParserResult(Type t){
		this.type=t;
		this.value=null;
	}

	public ParserResult(Object v){
		this.value=v;
		this.type=null;
	}

	public ParserResult(Object v, Type t){
		this.type=t;
		this.value=v;
	}
	
}
