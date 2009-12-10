#!/usr/bin/env python
#encoding=utf-8
import os

"""
	Set de pruebas a correr sobre el análisis semántico.	
	Las pruebas son de la forma: propósito, archivo.
"""

root='$HOME/code_snippets/'

pruebas=[
('Probar expresiones con operaciones aritméticas de tipos numéricos incompatibles', 'expressionerrors.adb'),
('Probar asignaciones inválidas: tipos inexistentes, llamadas a funciones, variables, asignaciones incompatibles', 'assignmentTest.adb'),
('Probar ámbitos y asignación de constantes.', 'scopeTest.adb'),
('Probar llamadas a funciones y sentencias de retorno faltantes', 'functionTest.adb'),
('Probar if y loop statements', 'statementsTest.adb'),
('Probar creación de records y acceso a sus componentes', 'recordsTest.adb'),
]

#imprimir el menú de pruebas
print "#"*10+"PRUEBAS DISPONIBLES:"+"#"*10
i=1
for prueba in pruebas:
	print "Prueba %d: %s; en archivo '%s'\n"% (i,prueba[0], prueba[1])
	i+=1 

a_ejecutar=int(raw_input("Qué probamos?( 0 para probar todas )\n>"))-1
if a_ejecutar < len(pruebas):
	if a_ejecutar==-1:
		for prueba in pruebas:
			print "Prueba: %s"%prueba[0]
			print "="*10 + "EJECUCIÓN EN GNAT:"+"="*10
			os.popen('gcc -S %s'%root+prueba[1])
			print "$"*10 + "EJECUCIÓN EN MAIN:"+"$"*10
			os.popen('java Main %s' % root+prueba[1])
	else:
		print "="*10 + "EJECUCIÓN EN GNAT:"+"="*10
		os.popen('gcc -S %s'%root+pruebas[a_ejecutar][1])
		print "$"*10 + "EJECUCIÓN EN MAIN:"+"$"*10
		os.popen('java Main %s' % root+pruebas[a_ejecutar][1])
else:
	print "Prueba inválida!"
