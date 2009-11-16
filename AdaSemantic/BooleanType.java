package AdaSemantic;

public class BooleanType extends PrimitiveType{
        public BooleanType(){
                super();
                this.width=Type.BOOLEAN_WIDTH;
		this.name="boolean";
        }
        
	public BooleanType(String name){
                super();
    	        this.width=Type.BOOLEAN_WIDTH;
		this.name=name;
        }


        public String toString(){
		String n=(name.equalsIgnoreCase("boolean"))? "" : name+": ";
                return n+"Boolean";
        }

        public boolean equals(Object o){
                if(o==null)
                        return false;
                if(!(o instanceof BooleanType))
                        return false;
                if(o==this)
                        return true;
                //if(((BooleanType) o).getValue()==this.value)		
                return this.name.equalsIgnoreCase(((BooleanType)o).name);
        }
	
	public boolean isNumeric(){
		return false;
	}
}

