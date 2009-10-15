package AdaSemantic;

public class FloatType extends PrimitiveType{
        public FloatType(){
                super();
                this.width=Type.FLOAT_WIDTH;
        }

        public String toString(){
                return "Float";
        }

        public boolean equals(Object o){
                if(o==null)
                        return false;
                if(!(o instanceof FloatType))
                        return false;
                if(o==this)
                        return true;
                //if(((FloatType) o).getValue()==this.value)
                return true;
        }
}

