package CodeGeneration;
import java.util.HashSet;
import java.util.LinkedHashMap;
/**Descriptor de registros para el primer coprocesador*/
public class C1RegisterDescriptor extends RegisterDescriptor{
	public C1RegisterDescriptor(){
		descriptor=new LinkedHashMap<String, HashSet<String>>();
                //inicializar el mapa con los distintos registros:
                String[] info=floatTemps.split("_");
               
                //inicializar con los temporales
                int step=1;
                String[] bounds=null;
                String prefix="";
		prefix=info[0];
		step=Integer.parseInt(info[1]);
		bounds=info[2].split("-");                      
		for(int i=Integer.parseInt(bounds[0]);i<=Integer.parseInt(bounds[1]);i+=step){
			descriptor.put(String.format("%s%d",prefix,i), new HashSet<String>(1));
		}
	}
}
