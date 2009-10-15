package AdaSemantic;
/**A class that represents a non-terminal in a syntax directed translation*/
public class ParserResult{
	/**The type of the production*/
	public Type type;
	
	public ParserResult(){}

	public ParserResult(Type t){
		this.type=t;
	}
	
}
