/**A class to represent the symbols in the symbol table.
@author Luis Felipe Borjas Reyes
@date 23 Oct 09
*/
package AdaSemantic;

public class AdaSymbol{
	/**The type of this symbol*/
	public Type type;
	/**The relative memory address of this symbol. In hexadecimal format*/
	public long address;
	/**Whether this symbol is or not constant*/
	public boolean constant;

	public AdaSymbol(){
		this.type=null;
		this.address=0x0;
	}

	public AdaSymbol(Type t){
		this.type=t;
		this.address=0x0;
		this.constant=false;
	}
	
	public AdaSymbol(Type t, boolean c){
		this.type=t;
		this.address=0x0;
		this.constant=c;
	}

	public AdaSymbol(Type t, long a){
		this.type=t;
		this.address=a;
	}

	public String toString(){
		return "Symbol of type "+type.toString()+" in address "+String.valueOf(this.address);
	}

	
}
