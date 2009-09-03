/*Para leer un archivo cup y ver sacar las no terminales declaradas, y a los lados izquierdo y derecho de producciones
* para poder escribir éstas a un archivo, hacerles diff y ver si todo lo que se declaró se usó y todo lo que estaba en las producciones
* estaba declarado
*/
%%

%{
private  StringBuilder declaradas=new StringBuilder();
private  StringBuilder lado_izquierdo=new StringBuilder();
private  StringBuilder lado_derecho=new StringBuilder();
%}

NEWLINE=\r|\n|\r\n
WHITE_SPACE_CHAR=[\n\r\ \t\b\012]
whitespace=(WHITE_SPACE_CHAR | NEWLINE)+
palabras=[a-zA-Z0-9_]+
comments="/*"[.]"*/"| "//"[^\n\r]
//COMMENT_TEXT=([^*/\n]|[^*\n]"/"[^*\n]|[^/\n]"*"[^/\n]|"*"[^/\n]|"/"[^*\n])*
%state DECLARATION
%state IZQ
%state DER

%%

<YYINITIAL>{
	{comments}	{}
	{whitespace}	{}
	"non terminal"	{yybegin(DECLARATION);}
	"precedence left" {}
	"::="		{yybegin(DER);}
	[^a-zA-Z 0-9]+	{}
	//las minúsculas determinan el inicio de las reglas	
	[a-z_]+		{yybegin(IZQ);}
	
}

<DECLARATION>{
	","	{}
	[a-z_]	{declaradas.append(yytext()+"\n");}
	";"	{yybegin(YYINITIAL);}
}

<IZQ>{
	"::="	{yybegin(DER);}
	[^a-z_] {}
	[a-z_]	{lado_izquierdo.append(yytext()+"\n")}

}

<DER>{
	";"	{yybegin(YYINITIAL);}
	[^a-z_] {}
	[a-z_]	{lado_derecho.append(yytext()+"\n");}
}

[^]	{System.err.println("Token desconocido:"+yytext());}
