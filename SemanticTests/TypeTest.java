import AdaSemantic.*;
import java.util.ArrayList;
/**Tests for the type hierarchy*/
public class TypeTest{

	public static boolean compare(Object a, Object b){
		return ((Type)a).equals(b);
	}

	public static void testCompare(Object a, Object b){
		a=(Type)a;
		b=(Type)b;
		if(compare(a, b))
			System.out.println(a.toString()+" is the same as "+b.toString());
		else
			System.out.println(a.toString()+" is NOT the same as "+b.toString());
			
	}

	public static void main(String[] args){
		Type bool=new BooleanType();
		Type bool2=new BooleanType();
		Type integer=new IntegerType();
		ArrayList<Type> fun1Domain=new ArrayList<Type>();
		fun1Domain.add(new IntegerType());fun1Domain.add(new FloatType());
		Type fun1=new FunctionType(new BooleanType(), fun1Domain);
		Type fun2=new FunctionType(new FloatType(), fun1Domain);
		ArrayList<Type> fun2Domain=new ArrayList<Type>();
		fun2Domain.add(new FloatType());fun2Domain.add(new IntegerType());
		Type fun3=new FunctionType(new BooleanType(), fun2Domain);
		Type fun4=new FunctionType(new BooleanType(), fun1Domain);
		Type rec1=new RecordType(fun1Domain);
		Type rec2=new RecordType(fun1Domain);
		Type rec3=new RecordType(fun2Domain);
		
		//test them!
		testCompare(bool, bool2);
		testCompare(bool, integer);
		testCompare(fun1, fun2);
		testCompare(fun1, fun3);
		testCompare(fun2, fun3);
		testCompare(fun1, fun4);
		testCompare(rec2, rec1);
		testCompare(rec2, rec3);
	}
	
}
