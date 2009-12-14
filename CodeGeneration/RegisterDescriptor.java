package CodeGeneration;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
/**El descriptor de registros de MIPS: es un mapa de registro a un arreglo 
a las variables que están en él*/

public class RegisterDescriptor{
	/*Constantes para los temporales disponibles en mips*/
	public String temps="%t_1_0-9";
	public String savedTemps="%s_1_0-7";
	public String floatTemps="%f_2_0-31";
	public HashMap<String, HashSet<String>> descriptor;

	public RegisterDescriptor(){

		descriptor=new HashMap<String, HashSet<String>>();
		//inicializar el mapa con los distintos registros:
		String[] t_info=temps.split("_");
		String[] s_info=savedTemps.split("_");
		String[][] meta={t_info, s_info};
		//inicializar con los temporales
		int step=1;
		String[] bounds=null;
		String prefix="";
		for(String[] info: meta ){
			prefix=info[0];
			step=Integer.parseInt(info[1]);
			bounds=info[2].split("-");			
			for(int i=Integer.parseInt(bounds[0]);i<=Integer.parseInt(bounds[1]);i+=step){
				descriptor.put(String.format("%s%d",prefix,i), new HashSet<String>());
			}
		}
	}

	public HashSet<String> get(String key){
		return descriptor.get(key);
	}

	public void update(String key, String var){
		descriptor.get(key).add(var);
	}

	public String toString(){
		StringBuilder s=new StringBuilder();
		for(Map.Entry entry:descriptor.entrySet()){
			s.append(String.format("%s:\t%s\n",entry.getKey(),entry.getValue()));
		}
		return s.toString();
	}
	
	public String getEmpty(){
		HashSet<String> value;
		for(Map.Entry entry: descriptor.entrySet()){
			value=(HashSet<String>)entry.getValue();
			if(value.isEmpty())
				return entry.getKey().toString();
		}
		return null;
	}
}
