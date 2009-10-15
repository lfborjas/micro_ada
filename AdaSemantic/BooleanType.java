package AdaSemantic;

public class BooleanType extends PrimitiveType{
        public BooleanType(){
                super();
                this.width=Type.BOOLEAN_WIDTH;
        }

        public String toString(){
                return "Boolean";
        }

        public boolean equals(Object o){
                if(o==null)
                        return false;
                if(!(o instanceof BooleanType))
                        return false;
                if(o==this)
                        return true;
                //if(((BooleanType) o).getValue()==this.value)
                return true;
        }
}

