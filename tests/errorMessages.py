#!/usr/bin/env python
#encoding=utf-8
import re
import os
ROOT_PATH=os.path.dirname(__file__)
HOME=os.environ['HOME']
with open(HOME+"/Escritorio/ada95_syntax.cup") as parser:
	result=open(ROOT_PATH+"/errorMessages.txt", 'w')
	for line in parser:
		m=re.match(r'\s*agregarError\((?P<mensaje>.*,)', line)	
		if m:
			result.write("%s\n"%m.group('mensaje'))
	
	result.close()		
