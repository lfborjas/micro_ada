package AdaSemantic;

public class StringType extends PrimitiveType{
	 /*Must provide the String's lenght!*/
	 public StringType(int len){
                super();
                this.width=len;
        }

        public String toString(){
                return "String";
        }

        public boolean equals(Object o){
                if(o==null)
                        return false;
                if(!(o instanceof StringType))
                        return false;
                if(o==this)
                        return true;
                //if(((StringType) o).getValue()==this.value)
                return true;
        }

}
