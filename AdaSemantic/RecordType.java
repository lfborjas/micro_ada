package AdaSemantic;
import java.util.ArrayList;

public class RecordType extends Type{
	public LinkedSymbolTable symbolTable;
	public String name;
	public RecordType(){
		super();
		symbolTable=new LinkedSymbolTable();
		name="";
	}
	/**Receives a symbol table, as it is important to know the names of the components*/
	public RecordType(String name, LinkedSymbolTable st){
		this.name=name;
		this.symbolTable=st;
		ArrayList<Type> product=new ArrayList<Type>();
		for(AdaSymbol e: st.getTable().values())
			product.add(e.type);
		this.product=product;
                for(Type t: this.product)
                        this.width+=t.getWidth();
	
	}	
	
	/**Get a component's type, based on the component name*/	
	public Type getComponentType(Object name){
		AdaSymbol component= this.symbolTable.get(name);
		if (component != null)
			return component.type;
		else
			return null;
	}

	public String toString(){
		StringBuilder retVal=new StringBuilder("Record ");
		if (!this.product.isEmpty())
			retVal.append(" of (");
		else
			return retVal.toString();
		
		for(int i=0;i<this.product.size()-1; i++)
			retVal.append(this.product.get(i)+ ", ");
		retVal.append(this.product.get(this.product.size()-1)+" )");
		return retVal.toString();
	
	}

	public boolean isPrimitive(){
		return false;
	}

	public boolean equals(Object o){
		if(o==null)
			return false;
		if(!(o instanceof RecordType))
			return false;
		if(o==this)
			return true;
		RecordType temp=(RecordType) o;
		//TODO: should I consider the name or the number, type and order of the params?
		/*
		if (!temp.getName().equalsIgnoreCase(this.name))
			return false;	
		*/
		//must have the same width!
		if (temp.getWidth() != this.width)
			return false;
		//if it does, then must have the same field number
		if(temp.getProduct().size() != this.product.size())
			return false;
		//if so, the fields must be the same types in the same order (?)
		ArrayList<Type> tempProduct=temp.getProduct();
		for(int i=0;i<this.product.size();i++)
			if(!(tempProduct.get(i).equals(this.product.get(i))))
				return false;
		return true;
	}
}
