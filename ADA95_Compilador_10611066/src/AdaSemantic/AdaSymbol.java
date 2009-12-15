/**A class to represent the symbols in the symbol table.
@author Luis Felipe Borjas Reyes
@date 23 Oct 09
*/
package AdaSemantic;
import java.util.HashSet;
import static CodeGeneration.VarInfo.UNUSED;
public class AdaSymbol{
	/**The type of this symbol*/
	public Type type;
	/**The relative memory address of this symbol. In hexadecimal format*/
	public long address;
	/**Whether this symbol is or not constant*/
	public boolean constant;
	/**The access descriptor: */
	public HashSet<String> accessDescriptor;
	/**Whether it is alive*/
	public boolean isAlive;
	/**When is it's next use*/
	public int nextUse;
	/**Como toda la info de las últimas tres columnas no se usará hasta después
	(en la generación de código) sobre símbolos ya creados, entonces defaultear*/
	private void initPostInfo(){
		this.isAlive=false;
		this.nextUse=UNUSED;
		this.accessDescriptor=new HashSet<String>();
	}
	public AdaSymbol(){
		this.type=null;
		this.address=0x0;
		initPostInfo();
	}

	public AdaSymbol(Type t){
		this.type=t;
		this.address=0x0;
		this.constant=false;
		initPostInfo();
	}
	
	public AdaSymbol(Type t, boolean c){
		this.type=t;
		this.address=0x0;
		this.constant=c;
		initPostInfo();
	}

	public AdaSymbol(Type t, long a){
		this.type=t;
		this.address=a;
		initPostInfo();
	}
	
	public AdaSymbol(Type t, boolean cons, String initialPlace){
		this.type=t;
		this.address=0x0;
		this.constant=cons;
		this.isAlive=false;
		this.nextUse=UNUSED;
		this.accessDescriptor=new HashSet<String>();
		this.accessDescriptor.add(initialPlace);
	}
	public String toString(){
		String cons=(constant) ? "Constant ": "";
		//return cons+type.toString()+" @ "+String.valueOf(this.address);
		return String.format("%s%s@%s|alive: %s next use: %d; stored in %s",
					 cons, type, address, isAlive, nextUse, accessDescriptor);
	}

	
}
