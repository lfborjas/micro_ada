/**Elementos léxicos del lenguaje de programación ADA-95.
*Autor: Luis Felipe Borjas Reyes @ 25 Agosto 2009
*
*/
/*Correr después del parser!*/
import java_cup.runtime.*;

%%
/*El código de usuario*/
%class Ada95Lexer
%unicode
%cup
%line
%column
/*Ada95 es case insensitive*/
%ignorecase

%{
StringBuffer string=new StringBuffer();
/**Función para crear un nuevo símbolo de java_cup.runtime vacío*/
private Symbol symbol(int type){
	return new Symbol(type,yyline,yycolumn);
}

/**Función para crear un nuevo símbolo de java_cup.runtime con valor
*@ param value el valor del símbolo
*/
private Symbol symbol(int type, Object value){
	return new Symbol(type, yyline, yycolumn, value);
}

%}

/*Los elementos léxicos de ADA*/

%%
/*Las reglas léxicas*/

/*Si la entrada no pega con nada, devolver error léxico*/
[^]    { throw new Error("Caracter inesperado: <"+yytext()+"> en línea "+yyline+"."); }


