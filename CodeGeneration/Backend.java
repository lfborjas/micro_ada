package CodeGeneration;
import AdaSemantic.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.HashSet;
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
	/*Expresiones regulares*/
	private final static String DECLARERS="function|glbl|record";
	private final static String BBDELIMITERS="exit|if.*|goto|glblExit";
	private final static String BEGINNERS="initFunction|initRecord";
	private final static String JUMPS="if.*|goto";
	private final static String ENDERS="exit|glblExit";
	private final static String ERASABLES="function|record";
	/*TODO: AQUI FIJO VAN MÁS COSAS*/

	/*Lo que ocupa internamente*/
	private ArrayList<BasicBlock> basicBlocks;
	private StringBuilder data;
	private StringBuilder text;

	/**El constructor, recibe del front-end las cosas y del main si debuggear*/
	public Backend(ArrayList<Cuadruplo> i, FlatSymbolTable t, boolean dbg){
		data=new StringBuilder("\t.data\n");
		text=new StringBuilder("\t.text\n");
		this.basicBlocks=new ArrayList<BasicBlock>();
		icode=i;
		st=t;
		DEBUG=true;//dbg;
		this.icode=reorderCode(this.icode);
		findBasicBlocks(this.icode);
	}

	/**El método que mueve todo el código de la parte declarativa de un subprograma 
	   al cuerpo. ¡Importante para poder generar los bloques básicos!*/	
	private ArrayList<Cuadruplo> reorderCode(ArrayList<Cuadruplo> code){
		if(DEBUG){
			System.out.println("Reordenando el código intermedio...");
		}
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
				if(!instruction.operador.matches(ERASABLES))
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
		System.out.println(String.format("Código reordenado: con %d cuádruplos",reordered.size()));
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

	/**El método que determina los bloques básicos: puesto que el código ya está reordenado, toma las
	   salidas, los if y los goto como los terminadores de un bloque. Luego de una salida
	   no debería haber nada más que una entrada a función o record, así que está bien, no?*/
	private void findBasicBlocks(ArrayList<Cuadruplo> code){
		if(DEBUG){System.out.println("Determinando bloques básicos...");}
		//encontrar las instrucciones líderes
		HashSet<Integer> leaderSet=new HashSet<Integer>();
		Integer iteration;
		Cuadruplo instruction;
		for(int i=1; i<code.size();i++){
			instruction=code.get(i);
			iteration=new Integer(i);
			if(instruction.operador.matches(JUMPS)){
				leaderSet.add(new Integer(instruction.res));
				if(i <= code.size()-1)					
					leaderSet.add(iteration+1);
			}else if(instruction.operador.matches(BEGINNERS)){
				leaderSet.add(iteration);
			}
			
		}
		//convertir el set en lista:		
		ArrayList<Integer> leaders=new ArrayList<Integer>();		
		leaders.addAll(leaderSet);
		//ordenar los líderes:
		Collections.sort(leaders);
		if(DEBUG){System.out.println(String.format("Son líderes: %s",leaders));}
		Integer leader;
		String label;
		int i;
		for(i=0;i<leaders.size()-1;i++){
			leader=leaders.get(i).intValue();
			instruction=code.get(leader);
			label=(instruction.operador.matches(BEGINNERS)) ? instruction.arg1 : String.format("%s%d", "L",i);
			this.basicBlocks.add(
					new BasicBlock(label, leader, leaders.get(i+1).intValue()-1)
				);
		}
		//terminar el último bloque básico:
		this.basicBlocks.add(new BasicBlock(String.format("%s%d", "L", i), leaders.get(i++), code.size()-1));
		
		if(DEBUG){
			for(BasicBlock b: this.basicBlocks)
				System.out.println(b);
		}
	}
}
