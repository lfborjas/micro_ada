Uso: java Main (-h|-help) | ([-d|-debug] <archivo>)

Donde las opciones son:
-h -help
	Para mostrar este archivo de ayuda
-d -debug
	Para mostrar el progreso del parser en el archivo. Si desea guardar los resultados en otro archivo,ejecute:
			java Main -debug <archivo> >& <ruta_a_archivo_de_log>
Y <archivo> es la ruta del archivo a compilar.


Este compilador reconoce un subconjunto del lenguaje de programación ADA: sólo subprogramas simples, sentencias loop, if y for básicas y declaración de Records. Tiene incluidas las funciones put() y get() y los tipos primitivos Integer, Float y Boolean.Los números con base y con exponente no se permiten, y serán transformados a su equivalente entero o de punto flotante, respectivamente.No se pueden declarar nuevos tipos ni hacer casteo a alguno de los tipos primitivos incluidos, y las instancias de objeto sólo pueden ser de un record definido por el usuario o de alguno de los tipos ya mencionados (integer, float o boolean). La función put también puede manejar cadenas de caracteres como parámetro, pero ese es el único caso en el que permite utilizarlas (a éstas o a los literales de caracter). Tampoco se manejan atributos de los objetos ni se permiten sentencias de tipo 'use'  o 'with'

Sí se permite la recursividad y el anidamiento de subprogramas.

El compilador, al leer los tokens, tratará de enmascarar los errores heurísticamente en los siguientes casos:

Errores léxicos relacionados con otros lenguajes, en concreto, los más comunes, los relacionados con C; estos errores se manejan devolviendo los operadores equivalentes de ADA-95 así:
Símbolo		Sustitición

'=='		'='
'!='		'/='
'&&'		'and then'
'||'		'or else'
'[' ']'		'(' ')'
'{' '}'		--

N.B: Nótese que las llaves se toman como delimitadores de ámbito(como en los lenguajes similares a C), y por eso se ignoran

En cuanto a valores numéricos, existen las siguientes reglas:

*Si el número contiene guiones bajos, se pasará a la tabla de símbolos sin éstos
*Como se ha mencionado antes, si el número es un número con base, se truncará a entero y se convertirá a decimal
*Si el número tiene exponente, se convertirá al equivalente de punto flotante, según la implementación de java.

En cuanto a literales de cadenas de caracteres, si una no se cierra, se considerará que toda la entrada, hasta el final
de la línea, pertenece a la cadena.

AUTOR
   Luis Felipe Borjas Reyes; número de cuenta 10611066; @16 Septiembre 2009, 2141

VÉASE TAMBIÉN
   La interfaz gráfica para el compilador: ejecute el siguiente comando desde la carpeta del proyecto: java - jar ejecutables/dist/ADA95_Compilador_10611066.jar
   (Se requiere java 6 o superior)

