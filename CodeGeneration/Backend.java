package CodeGeneration;
import AdaSemantic.*;
import java.util.ArrayList;
import java.util.Stack;
/**
La súper clase de generación de código.
Genera código final para MIPS
@author Luis Felipe Borjas
@date	11 dic 2009; 2353 hrs

Su entrada consiste en el código intermedio y la tabla de símbolos aplanada que le pasa el 
front-end. Antes de generar el código, particiona éste en bloques básicos y genera 
la información de siguiente uso.
*/

public class Backend{
	/*Lo que le pasa el backend*/
	public ArrayList<Cuadruplo> icode;
	public FlatSymbolTable st;
	
	/**Para debugear:*/
	public boolean DEBUG;
	/*Lo que ocupa internamente*/
	private ArrayList<BasicBlock> basicBlocks;
	private final static String DECLARERS="function|glbl|record";
	private final static String BBDELIMITERS="initFunction|initRecord|if.*|goto";
	private final static String BEGINNERS="initFunction|initRecord";
	private final static String JUMPS="if.*|goto";
	private final static String ENDERS="exit|glblExit";
	/*TODO: AQUI FIJO VAN MÁS COSAS*/

	/**El constructor, recibe del front-end las cosas y del main si debuggear*/
	public Backend(ArrayList<Cuadruplo> i, FlatSymbolTable t, boolean dbg){
		icode=i;
		st=t;
		DEBUG=true;//dbg;
		this.icode=reorderCode(this.icode);
		//findBasicBlocks(this.icode);
	}

	/**El método que mueve todo el código de la parte declarativa de un subprograma 
	   al cuerpo. ¡Importante para poder generar los bloques básicos!*/	
	private ArrayList<Cuadruplo> reorderCode(ArrayList<Cuadruplo> code){
		ArrayList<Cuadruplo> reordered=new ArrayList<Cuadruplo>	();
		//cada vez que encuentre un function, poner la parte declarativa acá
		Stack<ArrayList<Cuadruplo>> declarativePart=new Stack<ArrayList<Cuadruplo>>();
		//acordate de saltarte los records!
		boolean stackMode=false;		
		ArrayList<Cuadruplo> toStack=new ArrayList<Cuadruplo>();
		//reordenamiento propio
		declarativePart.push(new ArrayList<Cuadruplo>());
		for(Cuadruplo instruction: code){
			if(reordered.contains(instruction))
				continue;
			//si es un declarador, meter todo en el stack
			if(instruction.operador.matches(DECLARERS)){
				stackMode=true;
				if(!toStack.isEmpty()){
					declarativePart.push(toStack);
					toStack=new ArrayList<Cuadruplo>();
				}
				reordered.add(instruction);
				continue;
			}else if(instruction.operador.matches(BEGINNERS)){
				if(!toStack.isEmpty()){
					declarativePart.push(toStack);
					toStack=new ArrayList<Cuadruplo>();
				}
				stackMode=false;
				reordered.add(instruction);
				if(!declarativePart.isEmpty()){
					reordered.addAll(declarativePart.pop());
					if(!declarativePart.isEmpty())
						toStack=declarativePart.pop();
					else
						toStack=new ArrayList<Cuadruplo>();
				}
				
				continue;
			}else if(instruction.operador.matches(ENDERS)){
				stackMode=true;
				reordered.add(instruction);
				continue;
			}else if(stackMode){
				toStack.add(instruction);
				continue;
			}else{
				reordered.add(instruction);
			}
	
				
		}
		System.out.println(reordered.size());
		//ahora, arreglar los gotos:
		int j=0;
		for(Cuadruplo i: reordered){
			if(i.operador.matches(JUMPS)){
				i.res=String.valueOf(Integer.parseInt(i.res)
			              -(code.indexOf(i)-reordered.indexOf(i)));
			}
			if(DEBUG){
				System.out.println(String.format("%d\t%s",j++,i));
			}
		}

	return reordered;	
	}

	/**El método que determina los bloques básicos*/
	private void findBasicBlocks(ArrayList<Cuadruplo> code){
		for(Cuadruplo instruction : code){
		/**TODO*/
		}
		
		
		if(DEBUG){
			for(BasicBlock b: this.basicBlocks)
				System.out.println(b);
		}
	}
}
