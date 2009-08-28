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
special_character=[^a-zA-Z 0-9]
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
decimal_literal={numeral}({point}{numeral})?{exponent}?
/*Lo necesario para números con base*/
extended_digit={digit}|[a-fA-F]
based_numeral={extended_digit}({underline}?{extended_digit})*
base={numeral}
based_literal={base}{num_sign}{based_numeral}({point}{based_numeral})?{num_sign}{exponent}?
/*Las literales numéricas*/
numeric_literal={decimal_literal}|{based_literal}
/*Identificadores y operadores*/
identifier={identifier_letter}({underline}({identifier_letter}|{digit}))*
relational_operator="/="|"="|"<"|">"|"<="|">="
adding_operator="+"|"-"
concatenate="&"
//actually, mod and rem are also multiplying operators:
multiplying_operator="*"|"/"
/*Para strings, si el estado no funciona:
string_element=([^\"]|{graphic_character})
string_literal=\"({string_element})*\"
*/
/*I've decided to better check for these in the grammar: */
//logical_operator="and"|"or"|"xor"
//highest_precedence_operator="**"|"not"|"abs"
//shortcut_operator="and then"|"or else"
//membership_test="in"|"not in"



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
{whitespace} 	{/*return symbol(sym.SEPARATOR);*/}
/*primero los operadores*/
{relational_operator}	{return symbol(sym.RELATIONAL_OPERATOR,yytext());}
{adding_operator}	{return symbol(sym.ADDING_OPERATOR,yytext());}
{concatenate}		{return symbol(sym.CONCATENATE);}
{multiplying_operator}	{return symbol(sym.MULTIPLYING_OPERATOR,yytext());}

/*Las palabras reservadas: declararlas como terminales en el .cup*/
"abort"	{return symbol(sym.ABORT);}
"abs"	{return symbol(sym.ABS);}
"abstract"	{return symbol(sym.ABSTRACT);}
"accept"	{return symbol(sym.ACCEPT);}
"access"	{return symbol(sym.ACCESS);}
"aliased"	{return symbol(sym.ALIASED);}
"all"	{return symbol(sym.ALL);}
"and"	{return symbol(sym.AND);}
"array"	{return symbol(sym.ARRAY);}
"at"	{return symbol(sym.AT);}

"begin"	{return symbol(sym.BEGIN);}
"body"	{return symbol(sym.BODY);}

"case"	{return symbol(sym.CASE);}
"constant"	{return symbol(sym.CONSTANT);}

"declare"	{return symbol(sym.DECLARE);}
"delay"	{return symbol(sym.DELAY);}
"delta"	{return symbol(sym.DELTA);}
"digits"	{return symbol(sym.DIGITS);}
"do"	{return symbol(sym.DO);}

"else"	{return symbol(sym.ELSE);}
"elsif"	{return symbol(sym.ELSIF);}
"end"	{return symbol(sym.END);}
"entry"	{return symbol(sym.ENTRY);}
"exception"	{return symbol(sym.EXCEPTION);}
"exit"	{return symbol(sym.EXIT);}

"for"	{return symbol(sym.FOR);}
"function"	{return symbol(sym.FUNCTION);}

"generic"	{return symbol(sym.GENERIC);}
"goto"		{return symbol(sym.GOTO);}

"if" 	{return symbol(sym.IF);}
"in" 	{return symbol(sym.IN);}
"is" 	{return symbol(sym.IS);}

"limited"	{return symbol(sym.LIMITED);}
"loop"		{return symbol(sym.LOOP);}

"mod"	{return symbol(sym.MOD);}

"new"	{return symbol(sym.NEW);}
"not"	{return symbol(sym.NOT);}
"null"	{return symbol(sym.NULL);}

"of"	{return symbol(sym.OF);}
"or"	{return symbol(sym.OR);}
"others" {return symbol(sym.OTHERS);}
"out"	{return symbol(sym.OUT);}

"package"	{return symbol(sym.PACKAGE);}
"pragma"	{return symbol(sym.PRAGMA);}
"private"	{return symbol(sym.PRIVATE);}
"procedure"	{return symbol(sym.PROCEDURE);}
"protected"	{return symbol(sym.PROTECTED);}

"raise"	{return symbol(sym.RAISE);}
"range"	{return symbol(sym.RANGE);}
"record" {return symbol(sym.RECORD);}
"rem"	{return symbol(sym.REM);}
"renames"	{return symbol(sym.RENAMES);}
"requeue"	{return symbol(sym.REQUEUE);}
"return"	{return symbol(sym.RETURN);}
"reverse"	{return symbol(sym.REVERSE);}

"select"	{return symbol(sym.SELECT);}
"separate"	{return symbol(sym.SEPARATE);}
"subtype"	{return symbol(sym.SUBTYPE);}

"tagged"	{return symbol(sym.TAGGED);}
"task"		{return symbol(sym.TASK);}
"terminate"	{return symbol(sym.TERMINATE);}
"then"		{return symbol(sym.THEN);}
"type"		{return symbol(sym.TYPE);}

"until"		{return symbol(sym.UNTIL);}
"use"		{return symbol(sym.USE);}

"when"		{return symbol(sym.WHEN);}
"while"		{return symbol(sym.WHILE);}
"with"		{return symbol(sym.WITH);}

"xor"		{return symbol(sym.XOR);}






/*Ahora, lo demás:*/
{identifier}	{return symbol(sym.IDENTIFIER, yytext());}
{numeric_literal}	{return symbol(sym.NUMERIC_LITERAL,yytext());}
{character_literal}	{return symbol(sym.CHARACTER_LITERAL,yytext());}
/*Manejar las strings: */
\"	{string.setLength(0);yybegin(STRING);}


/*Delimitadores como acciones de YYINITIAL*/
"("	{return symbol(sym.LEFTPAR);}
")"	{return symbol(sym.RIGHTPAR);}
","	{return symbol(sym.COMMA);}
"."	{return symbol(sym.POINT);}
":"	{return symbol(sym.COLON);}
";"	{return symbol(sym.SEMICOLON);}
"|"	{return symbol(sym.VERTICAL_LINE);}
":="	{return symbol(sym.ASSIGNMENT);}
"=>"	{return symbol(sym.ARROW);}
".."	{return symbol(sym.DOUBLEDOT);}
"<<"	{return symbol(sym.LEFTLABEL);}
">>"	{return symbol(sym.RIGHTLABEL);}
"<>"	{return symbol(sym.BOX);}
"**"	{return symbol(sym.EXPONENTIATE);}
/*
El RM no usa los siguientes cuatro como delimitadores ¿debería permitirlos?
"["	{return symbol(sym.LEFT_BRACKET);}
"]"	{return symbol(sym.RIGHT_BRACKET);}
"{"	{return symbol(sym.LEFT_BRACE);}
"}"	{return symbol(sym.RIGHT_BRACE);}
*/
/*El RM no define al underline como separador*/
//{underline} {return symbol(sym.UNDERLINE);}
}


<STRING>{
/*El RM dice que la doble comilla se considera un elemento del string:*/
{double_quote}	{string.append(yytext());}
/*Cuando encuentre el final, retornarlo con todo lo que haya puesto en la cadena*/
\"	{yybegin(YYINITIAL);
	 return symbol(sym.STRING_LITERAL,string.toString());}
/*Todo lo que no sea salto de línea o cierre se vale*/
[^\"\n\r]+	{string.append(yytext());}
/*ADA no permite saltos de línea en string literals*/
[\n\r]		{System.err.println("Error léxico: no es permitido un salto de línea en una cadena de caracteres. En línea "+yyline+", columna "+yycolumn);}
}

/*Si la entrada no pega con nada, devolver error léxico*/
[^]    { /*throw new Error("Caracter inesperado: <"+yytext()+"> en línea "+yyline+", columna "+yycolumn);*/
	System.err.println("Caracter inesperado: <"+yytext()+"> en línea "+yyline+", columna "+yycolumnn); }


