import java.io.*;
import java.util.ArrayList;
/*Idea, manejar argumentos como help y debug... Pasarlos a una cosa tipo diccionario*/
/**Clase que provee una CLI al mini-compilador de ADA95
@autor Luis Felipe Borjas  10611066
*/
public class Main {
/**La String que dice cómo usarlo: */
  static private String usage="Uso: java Main (-h|-help) | ([-d|-debug] <archivo>)";
  /**Para leer el contenido de un archivo de texto: sacado de 
    http://www.javapractices.com/topic/TopicAction.do?Id=42*/
    static public String getContents(File aFile) {
        //...checks on aFile are elided
        StringBuilder contents = new StringBuilder();

        try {
            //use buffering, reading one line at a time
            //FileReader always assumes default encoding is OK!
            BufferedReader input = new BufferedReader(new FileReader(aFile));
            try {
                String line = null; //not declared within while loop
        /*
                 * readLine is a bit quirky :
                 * it returns the content of a line MINUS the newline.
                 * it returns null only for the END of the stream.
                 * it returns an empty String if two newlines appear in a row.
                 */
                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {//just don't say a word...
            //ex.printStackTrace();
        }

        return contents.toString();
    }
/**El método que imprime el uso y el archivo de ayuda y termina la ejecución*/
  static private void printHelp(boolean verbose){
	File ayuda=new File("README.txt");
	//si el archivo existe, imprimir lo demaś:
        if (verbose && ayuda.exists())
		System.out.println(getContents(ayuda));
	else//imprimir el uso:	
		System.out.println(usage);
	//ya imprimimos la ayuda, adiós:
	System.exit(0);
  } 

  /**El main en sí, recibe com argumentos opcionales si se quiere en modo debug o ayuda y obligatorios, el archivo*/
  static public void main(String argv[]) {
    
    boolean debug=false;
    //boolean help=false;
    /*Check if it actually comes something:*/
    
    switch(argv.length){
	case 0:
		printHelp(false);
		break;
	case 1://may be help or just the file
		if (argv[0].matches("-h|-help"))
			printHelp(true);
		break;
	case 2://can only be debug:
		if (argv[0].matches("-d|-debug"))
			debug=true;
		else
			printHelp(false);
		break;
	default://if none of the above: malformed 
		printHelp(false); 
		break;
		
    }
    /*un código pinta para sacar flags en desorden:	
    //Set the flags
    for(int i=0;i<argv.length-1;i++){
	//for debug:
    	if(argv[i].matches("-d|-debug"))	
		debug=true;
	else if(argv[i].matches("-h|-help")){
		printHelp();//help=true;
	}
    }
    */
    /*If help, print the help file and exit*/
    //if(help){printHelp();}
    /* Start the parser */
    String filename=argv[argv.length-1];
    try {     	
      long start=System.currentTimeMillis();
      parser p = new parser(new Ada95Lexer(new FileReader(filename)));
      if(debug){	
	      Object result = p.debug_parse().value;
	}else{
	     Object result = p.parse().value;
	}
	//sacar los errores y advertencias del parser
	ArrayList<String> parserErrores=p.getErrores();
	ArrayList<String> parserAdvertencias=p.getAdvertencias();
	//imprimir advertencias primero
	for(String advertencia: parserAdvertencias){
		System.err.println(advertencia);
	}
	//ahora, imprimir errores:
	for(String error:parserErrores){
		System.err.println(error);
	}
	//en todo caso, imprimir sumario de advertencias:
	String pluralize_warnings="advertencia";
	pluralize_warnings+= (parserAdvertencias.size()==1)? "":"s";
	if(parserAdvertencias.size()>0){		
		System.err.println((parserAdvertencias.size())+" "+pluralize_warnings+".");
	}
	//terminar la medición:
	long end=System.currentTimeMillis();
	float elapsed=(end-start);
	//sumario de errores
	if(parserErrores.size()==0){		
		System.out.println("Compilación exitosa, ("+elapsed+") milisegundos");	
	}else{
		String pluralize_finding="encontr";
		String pluralize_errors="error";
		pluralize_finding+= (parserErrores.size()==1)? "ó":"aron";
		pluralize_errors+= (parserErrores.size()==1)? "":"es";		
		System.err.println("Se "+pluralize_finding+" "+(parserErrores.size())+" "+pluralize_errors+".");
		System.err.println("Compilación fallida, ("+elapsed+") milisegundos");
	}
	
	
    }catch (FileNotFoundException fnfex){
	System.err.println("No se encuentra el archivo \""+filename+"\"");
	System.err.println(usage);
	System.exit(1);
    }catch (Exception e) {
      e.printStackTrace();
    }
  }
}

