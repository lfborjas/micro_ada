package CodeGeneration;
import AdaSemantic.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
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
	private final static String REGISTER="%[tsf][0-9]+";
	private final static String BRANCHES="goto|call";
	private final static String OPERATOR="if.*|add|sub|-|not|abs|mul|div|mod|rem";
	private final static String PROLOGUED="initFunction|initRecord";
	private final static String EPILOGUED="exit";
	private final static String IN_STACK="-?[0-9]+\\(\\$[sf]p\\)";

	public final static int  WORD_LENGTH=4;
	/*TODO: AQUI FIJO VAN MÁS COSAS*/

	/*Lo que ocupa internamente*/
	private ArrayList<BasicBlock> basicBlocks;
	private StringBuilder data;
	private StringBuilder text;
	//contiene los temporales y el siguiente uso de los mismos. 
	private HashMap<String, TempSymbol> frontEndTemps;
	//los descriptores de registros:
	private C1RegisterDescriptor floatDescriptor;
	private RegisterDescriptor   regDescriptor;
	/**El constructor, recibe del front-end las cosas y del main si debuggear*/
	public Backend(ArrayList<Cuadruplo> i, FlatSymbolTable t, boolean dbg){
		data=new StringBuilder("\t.data\n");
		text=new StringBuilder("\t.text\n");
		this.basicBlocks=new ArrayList<BasicBlock>();
		frontEndTemps=new HashMap<String, TempSymbol>();
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
				this.frontEndTemps.put(instruction.res, new TempSymbol(new VarInfo(false, UNUSED)));
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
					this.frontEndTemps.put(var, new TempSymbol(new VarInfo(isAlive, nextUse)));
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
	
	/**Obtiene la información de una variable*/
	private VarInfo getVarInfo(String var, String currentScope){
		AdaSymbol sym;
			if(!var.isEmpty() && !var.matches(NOT_VAR)){
				if(var.matches(FE_TEMP)){
					return this.frontEndTemps.get(var).info;
				}else{
					SymbolLookup t=this.st.get(currentScope, var);
					sym=(t==null)?null:t.symbol;
					if(sym != null){
						return new VarInfo(sym.isAlive, sym.nextUse);
					}else{
						return null;
					}
				}
			}else{
				return null;
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
						temp=this.frontEndTemps.get(dir.getValue()).info;
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

	/**La función para obtener los registros de una instrucción:
	   Por cuestiones de tiempo, asumiré que son enteros...
	   Las variables temporales SOLO pueden estar en registros, así que se les dará prioridad.
	   (es decir, nada de guardarlas en otro lado).	
	 */
	private HashMap<String, String> obtenReg(Cuadruplo I, 
						String currentScope, BasicBlock block, int instruction, boolean isCopy){
		//determinar el tipo: ¿cómo saber el tipo para instrucciones tipo $t1=$t1+$t3 ?
		/*En todas las operaciones enteras, el segundo operando puede ser una constante, entonces
		  Si es constante, no nos tomemos la molestia de buscarle un registro.*/
		HashMap<String, String> dirs=new HashMap<String, String>();		
		dirs.put("y", I.arg1);
		if(!isCopy){
			dirs.put("z", I.arg2);
			dirs.put("x", I.res);
		}
		HashMap<String, String> retVal=new HashMap<String, String>();
		//conseguir registros para x, y und z
		String key, value, register;		
		HashSet<String> accessDescriptor, regdesc;
		int regScore=0;
		HashMap<String, Integer> scores=new HashMap<String, Integer>();
		VarInfo v_info;
		boolean inReg=false;
		for(Map.Entry dir: dirs.entrySet()){
			key=dir.getKey().toString();
			value=dir.getValue().toString();
			//saltárselo si está vacío:
			if(value.isEmpty()){continue;}
			if(!value.matches(NOT_VAR)){
				//1. Ver si YA está en un registro.
				if(value.matches(FE_TEMP)){//es un temporal
					accessDescriptor=this.frontEndTemps.get(value).accessDescriptor;
				}else{
					accessDescriptor=this.st.get(currentScope, value).symbol.accessDescriptor;
				}	
				for(String place: accessDescriptor){
					//si está en un registro, devolverlo. Ya terminamos con esta var.
					inReg=false;
					if(place.matches(REGISTER)){						
						retVal.put(key, place );
						inReg=true;
						break;//ya encontramos que está en un registro!
					}					
				}//iterar en el descriptor de acceso
				if(inReg){continue;}
				//2. Aún aquí? Ok. Tratemos de darle un registro vacío:
				register=this.regDescriptor.getEmpty();
				if(register != null){
					retVal.put(key, register);
					continue;
				}
				boolean good=false;
				//3. OK, estamos fregados va? Bueno, busquemos registro por registro cuál darle!
				for(Map.Entry reg: this.regDescriptor.descriptor.entrySet()){
					regScore=0;
					regdesc=(HashSet<String>)reg.getValue();
					register=reg.getKey().toString();
					//por cada una de las variables en este descriptor, veamos qué ondas:
					for(String var: regdesc){
						if(var.matches(FE_TEMP)){
							accessDescriptor=this.frontEndTemps.get(var).accessDescriptor;
						}else{//es una variable normal
							accessDescriptor=this.st.get(currentScope,var).symbol.accessDescriptor;
						}
						String ix=dirs.get("x");
						v_info=getVarInfo(currentScope, var);
						//si el descriptor dice que está en algún otro lado...
						if(accessDescriptor.size()>1){
							good=true;		
						//si v es x y x no vuelve a salir en I:
						}else if(var.equals(ix) && !(ix.equals(dirs.get("y")) 
							|| ix.equals(dirs.get("z")))){
							good=true;
						}
						//si v no se utiliza otra vez en este bloque:
						else if(v_info.nextUse< instruction)
							good=true;
						//ni aún así? Generar un derrame:
						else if(!var.matches(FE_TEMP)){
							store(currentScope, var);
							regScore++;
						}
						if(good){break;}
						good=false;
					}//iterar en las variables del registro
					scores.put(register, new Integer(regScore));
				}//iterar en los registros
				//encontrar la menor score:
				int lesser=Integer.MAX_VALUE;
				int current;
				String lesserReg="";
				for(Map.Entry s: scores.entrySet()){
					current=((Integer)s.getValue()).intValue();
					if(current < lesser)
						lesserReg=s.getKey().toString();
					
				}
				retVal.put(value, lesserReg);
			}else{
				retVal.put(value, "");
			}
		}
		//si es copia, le damos a x el de y
		if(isCopy){retVal.put("x", retVal.get("y"));}
		return retVal;
	}//obtenerReg
	
	/**Para generar un guardado de una variable*/
	private void store(String var, String currentScope){
		long address=this.st.get(currentScope, var).symbol.address;
		text.append(String.format("\t sw %s, %d($fp)", var, address));
	}	
	
	/**En base a un número de instrucción, determina qué bloque básico la contiene y devuelve la etiqueta de éste*/
	private String getLabel(String destino){
		int destino_entero=Integer.parseInt(destino);
		for(BasicBlock b: this.basicBlocks){
			if(b.beginning>=destino_entero || b.end <= destino_entero)
				return b.label;
		}
		return "ERROR";
	}
	/**La función loca que hace la generación*/
	public void assemble(String filename){
		/*Hay dos tipos de instrucciones:
		 1. Las que ocupan registros: operaciones, copias, put/get y param.
			1.1 las copias son un caso especial.
		 2. Las que no: como los inits, exit y call*/			
		Cuadruplo instruction;
		int variable_space=0;
		String currentScope="";
		//poner global función principal y ponerle main también, porque MIPS la ocupa
		if(icode.get(1).operador.matches("glbl")){
			text.append(String.format("\t.globl %s\nmain:\n", icode.get(1).arg1));
			//currentScope=icode.get(1).arg1;			
		}
		for(BasicBlock block: this.basicBlocks){
			//poner la etiqueta:
			text.append(String.format("_%s:\n", block.label));
			//por cada instrucción en este bloque:
			for(int i=block.beginning; i<=block.end; i++){
				instruction=this.icode.get(i);
				//si es una prologada, poner el prólogo:
				
				//al final del bloque básico, guardar las variables vivas:
				if(i==block.end){
					/*TODO: guardar variables vivas!*/
				}
				
				if(instruction.operador.matches(PROLOGUED)){
					variable_space=WORD_LENGTH*Integer.parseInt(instruction.arg2);	
					//meter ra y fp:
					text.append("\t#PROLOGUE:\n");
					text.append(String.format("\tsub $sp, $sp, %d", 8+variable_space));
					text.append("\tsw $ra, ($sp)\n");
					text.append("\tsw $fp, 4($sp)\n");
					//reservar el espacio para variables:
					text.append(String.format("\tsub $fp, $sp, %d", variable_space));
					//inicializar el stack pointer:
					text.append("\tmove $sp, $fp\n\t#BODY:\n");					
					currentScope=instruction.arg1;
					continue;
				}//prologadas

				//si es un epílogo, escribirlo
				if(instruction.operador.matches(EPILOGUED)){
					text.append("\t#EPILOGUE:\n");
					//poner la etiqueta:
					text.append(String.format("\t_exit_%s:\n", instruction.arg1));
					//reestablecer el sp:
					text.append(String.format("\tadd $sp, $fp, %d\n", variable_space));
					//sacar fp:
					text.append("\tlw $fp, 4($sp)\n");
					//sacar ra:
					text.append("\tlw $ra, ($sp)\n");
					//regresar:
					text.append("\tjr $ra\n\n");
					continue;
				}//epilogadas
				
				 /*Las variables vivas ya debieron haber sido guardadas...*/
				if(instruction.operador.equalsIgnoreCase("goto")){
					//imprimir el salto
					text.append(String.format("\tb _%s\n", getLabel(instruction.arg1)));
					continue;
				}//goto
				if(instruction.operador.equalsIgnoreCase("call")){
					//imprimir el salto
					text.append(String.format("\tjal _%s\n", instruction.arg1));
					continue;
				}//call
				if(instruction.operador.equalsIgnoreCase("return")){
					//determinar el registro:
					String location=getLocation(currentScope, instruction.arg1);
					text.append(String.format("\t%s, $v0, %s\n",
								    getLoadInstruction(location), location));
					text.append(String.format("\tb _exit_%s", currentScope));
					continue;
				}//return
				if(instruction.operador.equalsIgnoreCase("glblExit")){
				//la etiqueta ya está generada...
					text.append(String.format("\tli $v0, 10\nsycall\n\n"));
				}

				/*Ahora, la hora de la verdad: las variables que ocupan registros!!*/
				
			}//por cada instrucción en el bloque
			
		}//por cada bloque
	}
	
	/**Mira el registro de acceso de la variable y devuelve una ubicación*/
	private String getLocation(String currentScope, String symbol){
		//si es constante, sólo regresarlo
		if(symbol.matches(CONSTANT))
			return symbol;
		//si es temporal, buscar allá		
		if(symbol.matches(FE_TEMP)){
			return this.frontEndTemps.get(symbol).accessDescriptor.iterator().next().toString();	
		}
		//si no, es variable, buscar en la st
		return this.st.get(currentScope, symbol).symbol.accessDescriptor.iterator().next().toString();
	
	}

	private String getLoadInstruction(String var){
		if(var.matches(CONSTANT))
			return "li";
		if(var.matches(REGISTER))
			return "move";
		if(var.matches(IN_STACK))
			return "lw";
		//si sale otra cosa, estamos mal!!
		return "ERROR";
	}
	
	private void writeCodeFile(String filename){
		File archivo=new File(filename);
		BufferedWriter out=null;
		try{
			out=new BufferedWriter(new FileWriter(archivo));
			out.write(data.toString());out.newLine();
			out.write(text.toString());out.newLine();
		}catch(FileNotFoundException fnfex){
			System.err.printf("No se encuentra el archivo %s", filename);
		}catch(IOException ioex){
			System.err.printf("No se pudo escribir el archivo %s", filename);
		}finally{
			try{
				out.close();	
			}catch(IOException fnx){
				System.err.printf("No se encuentra el archivo %s", filename);
			}
		}
	}
	/*PONER ACÁ LAS FUNCIONES QUE ESCRIBEN EL CÓDIGO A UN ARCHIVO AHÍ...*/
}//generator
