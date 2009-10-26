package AdaSemantic;
import java.util.ArrayList;

public class FunctionType extends Type{
	/**a.k.a. the return type of the function*/
	private Type range;
	/*The domain of the function is the product of this Type*/
	
	public FunctionType(Type range){
		super();
		this.range=range;
		this.width=range.width;
	}

	public FunctionType(Type range, ArrayList<Type> domain){
		this.product=domain;
		this.range=range;
		this.width=range.width;
	}

	public FunctionType(ArrayList<Type> domain){
		this.product=domain;
		this.width=0;
		this.range=null;//Should I create a void class?
	}

	
	public Type getRange(){
		return this.range;
	}

	public void setRange(Type r){
		range=r;
	}

	public boolean isPrimitive(){
		return false;
	}

	public String toString(){
		StringBuilder retVal=new StringBuilder();		
		if (this.range==null)
			retVal.append("Void ");
		else
			retVal.append(this.range.toString());
		retVal.append(" function with (");
		if (this.product.isEmpty()){
			retVal.append(" with no parameters.");
			return retVal.toString();
		}

		for(int i=0;i<this.product.size()-1;i++)
			retVal.append(this.product.get(i)+ ", ");
		retVal.append(this.product.get(product.size()-1)+ " )");
		return retVal.toString();			
	}

        public boolean equals(Object o){
                if(o==null)
                        return false;
                if(!(o instanceof FunctionType))
                        return false;
                if(o==this)
                        return true;
                FunctionType temp=(FunctionType) o;
                //must have the same width!
                if (temp.getWidth() != this.width)
                        return false;
                //if it does, then must have the same return type:
		if (!(temp.getRange().equals(this.range)))
			return false;
		//if it has the same return value, must have the same number of parameters
                if(temp.getProduct().size() != this.product.size())
                        return false;
                //if so, then the parameters must be equal in type and order:
                ArrayList<Type> tempProduct=temp.getProduct();
                for(int i=0;i<this.product.size();i++)
                        if(!(tempProduct.get(i).equals(this.product.get(i))))
                                return false;
                return true;
        }

		
}
