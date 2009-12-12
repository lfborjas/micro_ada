package CodeGeneration;
import AdaSemantic.AdaSymbol;
/**La clase para el resultado de una búsqueda en la tabla de símbolos aplanada.
No sólo es necesario el símbolo, si no también cuántos saltos se deben hacer
desde el registro de activación actual para llegar a él*/
public class SymbolLookup{
	public int saltos;
	public AdaSymbol symbol;
} 
