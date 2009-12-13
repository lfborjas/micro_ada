package CodeGeneration;
import AdaSemantic.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import static CodeGeneration.VarInfo.UNUSED;
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
	private final static String BEGINNERS="initFunction|initRecord|call";
	private final static String JUMPS="if.*|goto";
	private final static String ENDERS="exit|glblExit";
	private final static String ERASABLES="function|record";
	private final static String FE_TEMP="\\$t[0-9]+";
	private final static String PSEUDOINSTRUCTIONS="initRecord|initFunction|exit|call";
	private final static String INTEGER_LITERAL="[0-9]+";
	private final static String FLOAT_LITERAL="[0-9]+\\.[0-9]+";
	private final static String STRING_LITERAL="\".*\"";
	private final static String CONSTANT=String.format("%s|%s|%s", INTEGER_LITERAL, FLOAT_LITERAL, STRING_LITERAL);
	private final static String NOT_VAR=String.format("integer|string|float|boolean|%s", CONSTANT);
	/*TODO: AQUI FIJO VAN MÁS COSAS*/

	/*Lo que ocupa internamente*/
	private ArrayList<BasicBlock> basicBlocks;
	private StringBuilder data;
	private StringBuilder text;
	//contiene los temporales y el siguiente uso de los mismos. 
	private HashMap<String, VarInfo> frontEndTemps;
	//los descriptores de registros:
	private C1RegisterDescriptor floatDescriptor;
	private RegisterDescriptor   regDescriptor;
	/**El constructor, recibe del front-end las cosas y del main si debuggear*/
	public Backend(ArrayList<Cuadruplo> i, FlatSymbolTable t, boolean dbg){
		data=new StringBuilder("\t.data\n");
		text=new StringBuilder("\t.text\n");
		this.basicBlocks=new ArrayList<BasicBlock>();
		frontEndTemps=new HashMap<String, VarInfo>();
		floatDescriptor=new C1RegisterDescriptor();
		regDescriptor=new RegisterDescriptor();
		icode=i;
		st=t;
		DEBUG=dbg;
		this.icode=reorderCode(this.icode);
		findBasicBlocks(this.icode);
		getNextUse();
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
		if(DEBUG){
			System.out.println(String.format("Código reordenado: con %d cuádruplos",reordered.size()));
		}
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
			//llenar lo de los temporales:
			if(instruction.res.matches(FE_TEMP)){
				this.frontEndTemps.put(instruction.res, new VarInfo(false, UNUSED));
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
			label=(instruction.operador.matches("initFunction|initRecord")) ? instruction.arg1 : String.format("%s%d", "L",i);
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
	}//findBasicBlocks

	/**Función que establece la información de una variable*/
	private void setVarInfo(String var, String currentScope, boolean isAlive, int nextUse){
		AdaSymbol sym;
			if(!var.isEmpty() && !var.matches(NOT_VAR)){
				if(var.matches(FE_TEMP)){
					this.frontEndTemps.put(var, new VarInfo(isAlive, nextUse));
				}else{
					SymbolLookup t=this.st.get(currentScope, var);
					sym=(t==null)?null:t.symbol;
					if(sym != null){
						sym.isAlive=isAlive;
						sym.nextUse=nextUse;
					}
				}
			}
	}//setVarInfo

	/**La función que determina la información de siguiente uso de las direcciones en las instrucciones
	*  OBSTACULOS: alguna de las direcciones podría no estar en ninguna tabla o ser una constante
		       no debería generar para pseudo-instrucciones*/
	private void getNextUse(){
		if(DEBUG){System.out.println("Determinando la información de siguiente uso...");}
		Cuadruplo instruction;
		String currentScope="";
		VarInfo temp;
		AdaSymbol var;
		HashMap<String, String> dirs=new HashMap<String, String>(3);
		for(BasicBlock block: this.basicBlocks){
			for(int i=block.end; i>=block.beginning;i--){
				instruction=icode.get(i);
				if(instruction.operador.matches(ENDERS)){
					currentScope=instruction.arg1;				
				}
				//no generar info de siguiente uso para estas:
				if(instruction.operador.matches(PSEUDOINSTRUCTIONS))
					continue;
				//hay instrucciones que no tienen ninguna dirección
				if(!instruction.res.isEmpty()){dirs.put("res", instruction.res);}
				if(!instruction.arg1.isEmpty()){dirs.put("arg1", instruction.arg1);}
				if(!instruction.arg2.isEmpty()){dirs.put("arg2", instruction.arg2);}
				//adjuntar a la instrucción la info en la st de las variables
				for(Map.Entry dir: dirs.entrySet()){
					if(dir.getValue().toString().matches(FE_TEMP)){//es un temporal
						temp=this.frontEndTemps.get(dir.getValue());
						instruction.info.put(
							dir.getKey().toString(),
							temp
						);
					}else{//es una variable normal					
						SymbolLookup t=this.st.get(currentScope, dir.getValue().toString());
						var=(t==null)?null:t.symbol;
						if(var != null){
							instruction.info.put(
								dir.getKey().toString(),
								new VarInfo(var.isAlive, var.nextUse)
							);
						}
					}				
				}//fin del paso 1

				//poner en la tabla de símbolos al lado izquierdo como inactivo y no usado:
				setVarInfo(instruction.res, currentScope, false, UNUSED);
				//poner al lado derecho como activo y usado en esta instrucción:
				setVarInfo(instruction.arg1, currentScope, true, i);
				setVarInfo(instruction.arg2, currentScope, true, i);
		
			}//iterar en reversa sobre las instrucciones del bloque básico.
		}//por cada bloque básico

		if(DEBUG){
			System.out.println("La información de siguiente uso:");
			System.out.printf("La tabla de símbolos:\n %s ", this.st.toString());
			for(Map.Entry v: this.frontEndTemps.entrySet()){
				System.out.printf("%s: %s\n", v.getKey(), v.getValue());
			}
		}
	}//getNextuse

	/**La función para obtener los registros de una instrucción:*/
	private void siguienteReg(Cuadruplo I, int instruction, BasicBlock block){
		/*el algoritmo del libro...*/
	}
	
	/**La función loca que hace la generación*/
	public void assemble(){
			
	}

	/*PONER ACÁ LAS FUNCIONES QUE ESCRIBEN EL CÓDIGO A UN ARCHIVO AHÍ...*/
}//generator
