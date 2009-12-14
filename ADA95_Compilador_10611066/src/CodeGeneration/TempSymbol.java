package CodeGeneration;
import java.util.HashSet;
public class TempSymbol{
	public VarInfo info;
	public HashSet<String> accessDescriptor;
	public TempSymbol(VarInfo i){
		this.info=i;
		this.accessDescriptor=new HashSet<String>();
	}
	public TempSymbol(){
		this.info=new VarInfo();
		this.accessDescriptor=new HashSet<String>();
	}

	public String toString(){
		return String.format("%s in %s", info, accessDescriptor);
	}
}
