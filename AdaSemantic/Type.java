package AdaSemantic;
import java.util.ArrayList;
/**
A general super-class to manage the data types
*/
public abstract class Type{ 
	/**How many bytes are needed to store an object of this type*/
	protected int width;
	/**The cartesian product of types that represent this one*/
	protected ArrayList<Type> product;
	/**The name of this type*/
	public String name;
	
	/*Constants for the different widths*/
	public static final int INTEGER_WIDTH=4;
	public static final int FLOAT_WIDTH=8;
	public static final int BOOLEAN_WIDTH=1;
	
	public Type(String name){
		this.product=new ArrayList<Type>();
		this.width=0;
		this.name=name;
	}
	
	public Type(){
		this.product=new ArrayList<Type>();
		this.width=0;
		this.name="";
	}

	public Type(ArrayList<Type> t, int w){
		product=t;
		width=w;
		this.name="";
	}
	
	public Type(Type t, int w){
		this.product=new ArrayList<Type>();
		this.product.add(t);
		width=w; 
		this.name="";
	}
	
	public Type(Type t){
		this.product=new ArrayList<Type>();
		this.product.add(t);
		this.width=t.width;
		this.name="";
	}
	
	public Type(ArrayList<Type> tl){
		this.product=new ArrayList<Type>();
		this.product=tl;
		this.name="";
		for(Type t: tl)	
			this.width+=t.getWidth();
	}

	public Type(ArrayList<Type> t, int w, String name){
		this.product=t;
		this.width=w;
		this.name=name;
	}

	public Type(ArrayList<Type> t, String name){
		this.name="";
		this.product=t;
		this.width=0;
	}

	/*Setters and getters*/
	public void setWidth(int w){
		width=w;
	}
	public int getWidth(){
		return width;
	}
	
	public void setProduct(ArrayList<Type> p){
		product=p;
	}

	public void setProduct(Type t){
		this.product=new ArrayList<Type>();
		this.product.add(t);
	}
	
	public void addType(Type t){
		this.product.add(t);
	}

	public void removeType(Type t){
		this.product.remove(t);
	}

	public ArrayList<Type> getProduct(){
		return product;
	}
	
	
	
	/*The abstract methods*/
	public abstract boolean equals(Object o);
	public abstract String toString();
	public abstract boolean isPrimitive();
	
}
