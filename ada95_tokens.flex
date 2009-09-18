/**Elementos léxicos del lenguaje de programación ADA-95.
*Autor: Luis Felipe Borjas Reyes @ 25 Agosto 2009
*
*/
/*Correr después del parser!*/
import java_cup.runtime.*;
import java.util.ArrayList;
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
public ArrayList<String> lexical_errors=new ArrayList<String>();
public ArrayList<String> lexical_warnings=new ArrayList<String>();
private String currentText="";
public String getCurrentText(){return currentText;}
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
/**Función para truncar a entero un valor con base y luego convertirlo a un entero de base decimal*/
public Integer unbase_literal(String based){
        String clean=based.replaceAll("[_]|(\\.[_a-zA-Z0-9]*)", "");
        String[] terms=clean.split("#");
        return Integer.parseInt(terms[1],Integer.parseInt(terms[0]));
    }
%}

/*Los elementos léxicos de ADA*/
identifier_letter=[a-zA-Z]
digit=[0-9]
space_character=" "
underline="_"
num_sign="#"
point="."
plus="+"
minus="-"
double_quote=\"{2}
comment_start="--"
special_character=[^a-zA-Z 0-9\r\n\t\v\f]
format_effector=[\t\v\f]
line_terminator=\r|\n|\r\n
graphic_character={special_character}|{identifier_letter}|{digit}|{space_character}
separator={space_character}|{format_effector}|{line_terminator}
whitespace={separator}+
comment={comment_start}({graphic_character}|{format_effector})*{line_terminator}
/*Puse ? porque podría darse '' como literal de caracter*/
character_literal='{graphic_character}?'
/*Lo necesario para números decimales:*/
numeral={digit}({underline}?{digit})*
exponent=[Ee]{plus}?{numeral}|[Ee]{minus}{numeral}
/*Macro no usada, pero dejada porque va con el RM*/
decimal_literal={numeral}({point}{numeral})?{exponent}?
/*Lo necesario para números con base*/
extended_digit={digit}|[a-fA-F]
based_numeral={extended_digit}({underline}?{extended_digit})*
base={numeral}
based_literal={base}{num_sign}{based_numeral}({point}{based_numeral})?{num_sign}{exponent}?
/*Las literales numéricas: macro no usada*/
numeric_literal={decimal_literal}|{based_literal}
/*reglas para literales numéricas empotradas:*/
number=	{digit}{digit}*
floating_point_literal={numeral}{point}{numeral}
integer_literal={numeral}
/*Los números con exponente*/
power_literal={numeral}({point}{numeral})?{exponent}
/*Identificadores y operadores*/
identifier={identifier_letter}({underline}?({identifier_letter}|{digit}))*
/*Para strings, si el estado no funciona:
string_element=([^\"]|{graphic_character})
string_literal=\"({string_element})*\"
*/
//las literales booleanas:
boolean_literal="true"|"false"
and_then="and"{whitespace}"then"
or_else="or"{whitespace}"else"

%state STRING
%%

/*Las reglas léxicas*/


/*Manejando lo demás en función de YYINITIAL*/


<YYINITIAL>{
{comment}	{/*ignorar*/}
/*el ADA-RM dice que el whitespace es un separador que se requiere entre algunos elementos léxicos
*revisar eso en la gramática
*/
/*El ADA-RM dice que debe haber separadores entre algunas cosas ¿lo manejo acá?*/
{whitespace} 	{/*currentText=yytext();return symbol(sym.SEPARATOR);*/}
/*Los tipos primitivos: */
"boolean"	{currentText=yytext();return symbol(sym.BOOLEAN);}
"integer"	{currentText=yytext();return symbol(sym.INTEGER);}
"float"		{currentText=yytext();return symbol(sym.FLOAT);}
/*Las funciones hardcodeadas*/
"put"	{currentText=yytext();return symbol(sym.PUT);}
"get"	{currentText=yytext();return symbol(sym.GET);}

/*Los operadores compuestos: NO cumplen con la definición de operador del RM*/
{and_then}	{currentText=yytext();return symbol(sym.AND_THEN);}
{or_else}	{currentText=yytext();return symbol(sym.OR_ELSE);}
/*"not in"	{currentText=yytext();return symbol(sym.NOT_IN);}
*/
/*Ahora, las advertencias léxicas*/
/*Las palabras reservadas: declararlas como terminales en el .cup*/
"abort"	{currentText=yytext();return symbol(sym.ABORT);}
"abs"	{currentText=yytext();return symbol(sym.ABS);}
"abstract"	{currentText=yytext();return symbol(sym.ABSTRACT);}
"accept"	{currentText=yytext();return symbol(sym.ACCEPT);}
"access"	{currentText=yytext();return symbol(sym.ACCESS);}
"aliased"	{currentText=yytext();return symbol(sym.ALIASED);}
"all"	{currentText=yytext();return symbol(sym.ALL);}
"and"	{currentText=yytext();return symbol(sym.AND);}
"array"	{currentText=yytext();return symbol(sym.ARRAY);}
"at"	{currentText=yytext();return symbol(sym.AT);}

"begin"	{currentText=yytext();return symbol(sym.BEGIN);}
"body"	{currentText=yytext();return symbol(sym.BODY);}

"case"	{currentText=yytext();return symbol(sym.CASE);}
"constant"	{currentText=yytext();return symbol(sym.CONSTANT);}

"declare"	{currentText=yytext();return symbol(sym.DECLARE);}
"delay"	{currentText=yytext();return symbol(sym.DELAY);}
"delta"	{currentText=yytext();return symbol(sym.DELTA);}
"digits"	{currentText=yytext();return symbol(sym.DIGITS);}
"do"	{currentText=yytext();return symbol(sym.DO);}

"else"	{currentText=yytext();return symbol(sym.ELSE);}
"elsif"	{currentText=yytext();return symbol(sym.ELSIF);}
"end"	{currentText=yytext();return symbol(sym.END);}
"entry"	{currentText=yytext();return symbol(sym.ENTRY);}
"exception"	{currentText=yytext();return symbol(sym.EXCEPTION);}
"exit"	{currentText=yytext();return symbol(sym.EXIT);}

"for"	{currentText=yytext();return symbol(sym.FOR);}
"function"	{currentText=yytext();return symbol(sym.FUNCTION);}

"generic"	{currentText=yytext();return symbol(sym.GENERIC);}
"goto"		{currentText=yytext();return symbol(sym.GOTO);}

"if" 	{currentText=yytext();return symbol(sym.IF);}
"in" 	{currentText=yytext();return symbol(sym.IN);}
"is" 	{currentText=yytext();return symbol(sym.IS);}

"limited"	{currentText=yytext();return symbol(sym.LIMITED);}
"loop"		{currentText=yytext();return symbol(sym.LOOP);}

"mod"	{currentText=yytext();return symbol(sym.MOD);}

"new"	{currentText=yytext();return symbol(sym.NEW);}
"not"	{currentText=yytext();return symbol(sym.NOT);}
"null"	{currentText=yytext();return symbol(sym.NULL);}

"of"	{currentText=yytext();return symbol(sym.OF);}
"or"	{currentText=yytext();return symbol(sym.OR);}
"others" {currentText=yytext();return symbol(sym.OTHERS);}
"out"	{currentText=yytext();return symbol(sym.OUT);}

"package"	{currentText=yytext();return symbol(sym.PACKAGE);}
"pragma"	{currentText=yytext();return symbol(sym.PRAGMA);}
"private"	{currentText=yytext();return symbol(sym.PRIVATE);}
"procedure"	{currentText=yytext();return symbol(sym.PROCEDURE);}
"protected"	{currentText=yytext();return symbol(sym.PROTECTED);}

"raise"	{currentText=yytext();return symbol(sym.RAISE);}
"range"	{currentText=yytext();return symbol(sym.RANGE);}
"record" {currentText=yytext();return symbol(sym.RECORD);}
"rem"	{currentText=yytext();return symbol(sym.REM);}
"renames"	{currentText=yytext();return symbol(sym.RENAMES);}
"requeue"	{currentText=yytext();return symbol(sym.REQUEUE);}
"return"	{currentText=yytext();return symbol(sym.RETURN);}
"reverse"	{currentText=yytext();return symbol(sym.REVERSE);}

"select"	{currentText=yytext();return symbol(sym.SELECT);}
"separate"	{currentText=yytext();return symbol(sym.SEPARATE);}
"subtype"	{currentText=yytext();return symbol(sym.SUBTYPE);}

"tagged"	{currentText=yytext();return symbol(sym.TAGGED);}
"task"		{currentText=yytext();return symbol(sym.TASK);}
"terminate"	{currentText=yytext();return symbol(sym.TERMINATE);}
"then"		{currentText=yytext();return symbol(sym.THEN);}
"type"		{currentText=yytext();return symbol(sym.TYPE);}

"until"		{currentText=yytext();return symbol(sym.UNTIL);}
"use"		{currentText=yytext();return symbol(sym.USE);}

"when"		{currentText=yytext();return symbol(sym.WHEN);}
"while"		{currentText=yytext();return symbol(sym.WHILE);}
"with"		{currentText=yytext();return symbol(sym.WITH);}

"xor"		{currentText=yytext();return symbol(sym.XOR);}






/*Ahora, lo demás:*/
{boolean_literal}	{currentText=yytext();return symbol(sym.BOOLEAN_LITERAL,new Boolean(Boolean.parseBoolean(yytext())));}
{identifier}	{currentText=yytext();return symbol(sym.IDENTIFIER, yytext());}
//manejarlo así o de la otra manera?
/*{numeric_literal}	{currentText=yytext();return symbol(sym.NUMERIC_LITERAL,yytext());}*/
/*La función replaceAll quita los guiones bajos que ada permite en las literales numéricas*/
{integer_literal}	{currentText=yytext();return symbol(sym.INTEGER_LITERAL,new Integer(Integer.parseInt(yytext().replaceAll("_",""))));}
{floating_point_literal} {currentText=yytext();return symbol(sym.FLOATING_POINT_LITERAL,new Float(Float.parseFloat(yytext().replaceAll("_",""))));}	
//{character_literal}	{currentText=yytext();return symbol(sym.CHARACTER_LITERAL,new Character(yytext()));}
{character_literal}	{currentText=yytext();return symbol(sym.CHARACTER_LITERAL,yytext());}
/*Avisar que no se aceptan números con base:*/
{based_literal}		{lexical_warnings.add("Advertencia Léxica: Número ilegal (con base) '"+yytext()+"' sustituido por su equivalente entero:"+ unbase_literal(yytext()).toString()+" en línea "+String.valueOf(yyline+1)+", columna "+String.valueOf(yycolumn+1)); return symbol(sym.INTEGER_LITERAL,unbase_literal(yytext()));}
/*Avisar que tampoco se aceptan números con exponente: */
{power_literal}		{lexical_warnings.add("Advertencia léxica: Número ilegal (con exponente) '"+yytext()+"' sustituido por :"+Float.parseFloat(yytext().replaceAll("_",""))+" en línea "+String.valueOf(yyline+1)+", columna "+String.valueOf(yycolumn+1));return symbol(sym.FLOATING_POINT_LITERAL,new Float(Float.parseFloat(yytext().replaceAll("_",""))));}
/*Manejar las strings: */
\"	{string.setLength(0);yybegin(STRING);}

/*Advertencias léxicas*/
"&&"	{lexical_warnings.add("Advertencia léxica: se encontró '&&' en línea "+String.valueOf(yyline+1)+", columna "+String.valueOf(yycolumn+1)+" y debería ser 'and then'");
	currentText=yytext();return symbol(sym.AND_THEN);}
"||"	{lexical_warnings.add("Advertencia léxica: se encontró '||' en línea "+String.valueOf(yyline+1)+", columna "+String.valueOf(yycolumn+1)+" y debería ser 'or else'");
	currentText=yytext();return symbol(sym.OR_ELSE);}
"=="	{lexical_warnings.add("Advertencia léxica: se encontró '==' en línea "+String.valueOf(yyline+1)+", columna "+String.valueOf(yycolumn+1)+" y debería ser '='");
	currentText=yytext();return symbol(sym.EQUAL);}
"!="	{lexical_warnings.add("Advertencia léxica: se encontró '!=' en línea "+String.valueOf(yyline+1)+", columna "+String.valueOf(yycolumn+1)+" y debería ser '/='");
	currentText=yytext();return symbol(sym.INEQUALITY);}
"["	{lexical_warnings.add("Advertencia léxica: se encontró '[' en línea "+String.valueOf(yyline+1)+", columna "+String.valueOf(yycolumn+1)+" y se ha reemplazado por '('");
	currentText=yytext();return symbol(sym.LEFTPAR);}
"]"	{lexical_warnings.add("Advertencia léxica: se encontró ']' en línea "+String.valueOf(yyline+1)+", columna "+String.valueOf(yycolumn+1)+" y se ha reemplazado por ')'");
	currentText=yytext();return symbol(sym.RIGHTPAR);}
/*Debería ignorarlo o retornar paréntesis? El compilador GNAT de ADA tira paréntesis*/
"{"	{lexical_warnings.add("Advertencia léxica: se encontró '{' en línea "+String.valueOf(yyline+1)+", columna "+String.valueOf(yycolumn+1));
	currentText=yytext();}
"}"	{lexical_warnings.add("Advertencia léxica: se encontró '}' en línea "+String.valueOf(yyline+1)+", columna "+String.valueOf(yycolumn+1));
	currentText=yytext();}


/*Delimitadores como acciones de YYINITIAL*/
"&"	{currentText=yytext();return symbol(sym.CONCATENATE);}
"'"	{currentText=yytext();return symbol(sym.TICK);}
"("	{currentText=yytext();return symbol(sym.LEFTPAR);}
")"	{currentText=yytext();return symbol(sym.RIGHTPAR);}
"*"	{currentText=yytext();return symbol(sym.MULTIPLY);}
"+"	{currentText=yytext();return symbol(sym.PLUS);}
","	{currentText=yytext();return symbol(sym.COMMA);}
"-"	{currentText=yytext();return symbol(sym.MINUS);}
"."	{currentText=yytext();return symbol(sym.POINT);}
"/"	{currentText=yytext();return symbol(sym.DIVIDE);}
":"	{currentText=yytext();return symbol(sym.COLON);}
";"	{currentText=yytext();return symbol(sym.SEMICOLON);}
"<"	{currentText=yytext();return symbol(sym.LT);}
"="	{currentText=yytext();return symbol(sym.EQUAL);}
">"	{currentText=yytext();return symbol(sym.GT);}
"|"	{currentText=yytext();return symbol(sym.VERTICAL_LINE);}
"=>"	{currentText=yytext();return symbol(sym.ARROW);}
".."	{currentText=yytext();return symbol(sym.DOUBLEDOT);}
"**"	{currentText=yytext();return symbol(sym.EXPONENTIATE);}
":="	{currentText=yytext();return symbol(sym.ASSIGNMENT);}
"<<"	{currentText=yytext();return symbol(sym.LEFTLABEL);}
">>"	{currentText=yytext();return symbol(sym.RIGHTLABEL);}
"<>"	{currentText=yytext();return symbol(sym.BOX);}
"/="	{currentText=yytext();return symbol(sym.INEQUALITY);}
">="	{currentText=yytext();return symbol(sym.GTEQ);}
"<="	{currentText=yytext();return symbol(sym.LTEQ);}
/*
El RM no usa los siguientes cuatro como delimitadores ¿debería permitirlos?
"["	{currentText=yytext();return symbol(sym.LEFT_BRACKET);}
"]"	{currentText=yytext();return symbol(sym.RIGHT_BRACKET);}
"{"	{currentText=yytext();return symbol(sym.LEFT_BRACE);}
"}"	{currentText=yytext();return symbol(sym.RIGHT_BRACE);}
*/
/*El RM no define al underline como separador*/
//{underline} {currentText=yytext();return symbol(sym.UNDERLINE);}
}


<STRING>{
/*El RM dice que la doble comilla se considera un elemento del string:*/
{double_quote}	{string.append(yytext());}
/*Cuando encuentre el final, retornarlo con todo lo que haya puesto en la cadena*/
\"	{yybegin(YYINITIAL);
	 currentText=yytext();return symbol(sym.STRING_LITERAL,string.toString());}
/*Todo lo que no sea salto de línea o cierre se vale*/
[^\"\n\r]+	{string.append(yytext());}
/*ADA no permite saltos de línea en string literals*/
[\n\r]		{lexical_errors.add("Error léxico: literal de cadena de caracteres no cerrada. En línea "+String.valueOf(yyline+1)+", columna "+String.valueOf(yycolumn+1));yybegin(YYINITIAL); return symbol(sym.STRING_LITERAL, string.toString());}
}

/*Si la entrada no pega con nada, devolver error léxico*/
[^]    { /*throw new Error("Caracter inesperado: <"+yytext()+"> en línea "+yyline+", columna "+yycolumn);*/
	lexical_errors.add("Error léxico: caracter inesperado: '"+yytext()+"' en línea "+String.valueOf(yyline+1)+", columna "+String.valueOf(yycolumn+1)); }


