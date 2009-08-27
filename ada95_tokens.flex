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
based_literal={base}{num_sign}{based_numeral}({point}{based_numeral})?({num_sign}{exponent})?
numeric_literal={decimal_literal}|{based_literal}
identifier={identifier_letter}({underline}({identifier_letter}|{digit}))*
relational_operator="/="|"="|"<"|">"|"<="|">="
adding_operator="+"|"-"
concatenate="&"
//actually, mod and rem are also multiplying operators:
multiplying_operator="*"|"/"

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

/*Las palabras reservadas: */
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





/*Ahora, lo demás:*/
{identifier}	{return symbol(sym.ID, yytext());}
{numeric_literal}	{return symbol(sym.NUMERIC_LITERAL,yytext());}
{character_literal}	{return symbol(sym.CHAR_LIT,yytext());}
/*Manejar las strings: */
\"	{string.setLength(0);yybegin(STRING);}


/*Caracteres especiales como acciones de YYINITIAL*/
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
"**"	{return symbol(sym.EXPONENTIATE);}
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


