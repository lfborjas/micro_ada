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
comment_start="--"
special_character=[^a-zA-Z 0-9]
format_effector=[\t\v\f]
line_terminator=\r|\n|\r\n
graphic_character={special_character}|{identifier_letter}|{digit}|{space_character}
separator={space_character}|{format_effector}|{line_terminator}
whitespace={separator}+
comment={comment_start}({graphic_character}|{format_effector})*{line_terminator}
character_literal='{graphic_character}'
numeral={digit}({underline}{digit})*
exponent=[Ee]{plus}?{numeral}|[Ee]{minus}{numeral}
decimal_literal={numeral}({point}{numeral})?{exponent}?
extended_digit={digit}|[a-fA-F]
based_numeral={extended_digit}({underline}{extended_digit})*
base={numeral}
based_literal={base}{num_sign}{based_numeral}({point}{based_numeral})?{num_sign}{exponent}
numeric_literal={decimal_literal}|{based_literal}
identifier={identifier_letter}({underline}({identifier_letter}|{digit}))*
logical_operator="and"|"or"|"xor"
relational_operator="/="|"="|"<"|">"|"<="|">="
adding_operator="+"|"-"
concatenate="&"
multiplying_operator="*"|"/"|"mod"|"rem"
highest_precedence_operator="**"|"not"|"abs"
shortcut_operator="and then"|"or else"
membership_test="in"|"not in"



%state STRING
%%
/*Las reglas léxicas*/


/*Manejando lo demás en función de YYINITIAL*/
<YYINITIAL>{
/*primero los operadores*/
{logical_operator}	{return symbol(sym.LOG_OP,yytext());}
{relational_operator}	{return symbol(sym.REL_OP,yytext());}
{adding_operator}	{return symbol(sym.ADD_OP,yytext());}
{concatenate}		{return symbol(sym.CONCATENATE);}
{multiplying_operator}	{return symbol(sym.MULT_OP,yytext());}
{highest_precedence_operator} {return symbol(sym.HIGHEST_PRECEDENCE_OP,yytext());}
{shortcut_operator}	{return symbol(sym.SHORTCUT,yytext());}
{membership_test}	{return symbol(sym.MEMBERSHIP_TEST,yytext());}
/*luego, las palabras reservadas acá:*/

/*Ahora, lo demás:*/
{comment}	{/*ignore*/}
{whitespace} 	{/*ignore*/}
{identifier}	{return symbol(sym.ID, yytext());}
{numeric_literal}	{return symbol(sym.NUMERIC_LITERAL,yytext());}
/*Manejar las strings: */
\"	{string.setLength(0);yybegin(STRING);}
{character_literal}	{return symbol(sym.CHAR_LIT,yytext());}


/*Caracteres especiales como acciones de YYINITIAL*/
"'"	{return symbol(sym.TICK);}
"("	{return symbol(sym.LEFT_PAR);}
")"	{return symbol(sym.RIGHT_PAR);}
","	{return symbol(sym.COMMA);}
"."	{return symbol(sym.POINT);}
":"	{return symbol(sym.COLON);}
";"	{return symbol(sym.SEMICOLON);}
"|"	{return symbol(sym.VERTICAL_LINE);}
"["	{return symbol(sym.LEFT_BRACKET);}
"]"	{return symbol(sym.RIGHT_BRACKET);}
"{"	{return symbol(sym.LEFT_BRACE);}
"}"	{return symbol(sym.RIGHT_BRACE);}
":="	{return symbol(sym.ASSIGNMENT);}
"=>"	{return symbol(sym.ARROW);}
".."	{return symbol(sym.DOUBLEDOT);}
"<<"	{return symbol(sym.LEFTLABEL);}
">>"	{return symbol(sym.RIGHTLABEL);}
"<>"	{return symbol(sym.BOX);}
{underline} {return symbol(sym.UNDERLINE);}



}


<STRING>{
/*Cuando encuentre el final, retornarlo con todo lo que haya puesto en la cadena*/
\"	{yybegin(YYINITIAL);
	 return symbol(sym.STRING_LITERAL,string.toString());}
/*Todo lo que no sea salto de línea o cierre se vale*/
[^\"\n\r]+	{string.append(yytext());}
/*ADA no permite saltos de línea en string literals*/
[\n\r]		{System.err.println("Token inválido: no es permitido un salto de línea en una cadena de caracteres. En línea "+yyline+" columna "+yycolumn);}
}

/*Si la entrada no pega con nada, devolver error léxico*/
[^]    { throw new Error("Caracter inesperado: <"+yytext()+"> en línea "+yyline+"."); }


