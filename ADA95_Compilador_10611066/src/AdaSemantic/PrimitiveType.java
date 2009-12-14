package AdaSemantic;

public abstract class PrimitiveType extends Type{
	PrimitiveType(){
		super();
	}	
	
	public boolean isPrimitive(){
		return true;
	}
}
