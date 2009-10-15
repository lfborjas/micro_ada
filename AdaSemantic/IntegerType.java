package AdaSemantic;

public class IntegerType extends PrimitiveType{
	public IntegerType(){
		super();
		this.width=Type.INTEGER_WIDTH;
	}	
	
	public String toString(){
		return "Integer";
	}

	public boolean equals(Object o){
		if(o==null)
			return false;
		if(!(o instanceof IntegerType))
			return false;
		if(o==this)
			return true;
		//if(((IntegerType) o).getValue()==this.value)
		return true;
	}
}
