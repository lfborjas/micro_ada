package AdaSemantic;

public class IntegerType extends PrimitiveType{
	public IntegerType(){
		super();
		this.width=Type.INTEGER_WIDTH;
		this.name="integer";
	}
	
	public IntegerType(String name){
                super();
                this.width=Type.INTEGER_WIDTH;
                this.name=name;
        }

	public String toString(){
                String n=(name.equalsIgnoreCase("integer"))? "" : name+": ";
                return n+"Integer";
        }
	

	public boolean equals(Object o){
		if(o==null)
			return false;
		if(!(o instanceof IntegerType))
			return false;
		if(o==this)
			return true;
		//if(((IntegerType) o).getValue()==this.value)
		return this.name.equalsIgnoreCase(((IntegerType)o).name);
	}
	
	public boolean isNumeric(){
		return true;
	}
	
	public boolean isDiscrete(){
		return true;
	}
}
