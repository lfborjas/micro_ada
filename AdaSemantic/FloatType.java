package AdaSemantic;

public class FloatType extends PrimitiveType{
        public FloatType(){
                super();
                this.width=Type.FLOAT_WIDTH;
		this.name="float";
        }
	
	public FloatType(String name){
		super();
		this.width=Type.FLOAT_WIDTH;
		this.name=name;
	}

	public String toString(){
                String n=(name.equalsIgnoreCase("float"))? "" : name+": ";
                return n+"Float";
        }
        public boolean equals(Object o){
                if(o==null)
                        return false;
                if(!(o instanceof FloatType))
                        return false;
                if(o==this)
                        return true;
                //if(((FloatType) o).getValue()==this.value)
                return this.name.equalsIgnoreCase(((FloatType)o).name);
        }
	
	public boolean isNumeric(){
		return true;
	}
}

