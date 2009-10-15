package AdaSemantic;
import java.util.ArrayList;
public class ErrorType extends PrimitiveType{
	public ErrorType(Type expectedType){
		this.product=new ArrayList<Type>();
		this.product.add(expectedType);
		this.width=0;	
	
	}
	
	public String toString(){
		StringBuilder retVal=new StringBuilder("Error ");
		if (this.product.isEmpty())
			return retVal.toString();
		else
			return retVal.toString()+ ": se esperaba el tipo "+ this.product.get(0).toString();
	}

	/**An error is never equal to another*/
	public boolean equals(Object o){
		if(o==null)
			return false;
		if(o==this)
			return true;
		if(!(o instanceof Type))
			return false;
		//if the other Type is of the expected type, then they are equal (this is an attempt
		//to recover from semantic type errors
		if(!(this.product.get(0).toString().equals(o.toString())))
			return false;
		else 
			return true;
	}
	
}

