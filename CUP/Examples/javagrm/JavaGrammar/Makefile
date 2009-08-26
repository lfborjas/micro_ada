# Makefile to create simple test framework for the java parsers.
# Copyright (C) 1998 C. Scott Ananian <cananian@alumni.princeton.edu>
# Released under the terms of the GPL with NO WARRANTY. See COPYING.

# java environment.
JAVA=java
JAVAC=javac
JFLAGS=-g
#CUPFLAGS=-dump_states

# list the available java grammar versions
JAVA_GRAMMARS=10 11 12 14 15

all: $(foreach j,$(JAVA_GRAMMARS),Parse/Grm$(j).class) \
	Lex/Lex.class Main/Main.class

# Feed the appropriate CUP specification to javaCUP.
Parse/Grm%.java Parse/Sym%.java: Parse/java%.cup
	cd Parse && \
	${JAVA} java_cup.Main ${CUPFLAGS} -parser Grm$* -symbols Sym$* \
		< java$*.cup 2>Grm$*.err && tail Grm$*.err

# Compile the java source for the parser.
Parse/Grm%.class: Parse/Lexer.java Parse/Grm%.java Parse/Sym%.java
	${JAVAC} ${JFLAGS} $^

# Make the lexer symbols from the parser symbols.
Lex/Sym.java: $(foreach j,$(JAVA_GRAMMARS),Parse/Sym$(j).java)
# verify that these are all identical!
	@if cat $^ | sed -e 's/Sym[0-9][0-9]/Sym/g' | sort | uniq -c | \
	egrep -v '^[ ]*[0-9]*[05]	' | grep -v "^[ ]*[0-9]*[ 	]*//"\
	; then \
	  echo $^ "are not identical;" ;\
	  echo "we won't be able to build a single lexer for all of these." ;\
	  exit 1;\
	fi
# now make a generic version.
	sed -e "s/package Parse/package Lex/" -e "s/Sym10/Sym/g" \
		< Parse/Sym10.java > $@

# Compile the java source for the (unified) lexer.
Lex/Lex.class: Lex/*.java Lex/Sym.java
	${JAVAC} ${JFLAGS} Lex/*.java

# Compile the java source for the driver.
Main/Main.class: Main/Main.java
	${JAVAC} ${JFLAGS} Main/*.java

# run some quick tests.
test: Parse/Lexer.java Parse/Grm14.java all phony
	for n in 1 2 3 4 5; do \
	( ${JAVA} Main.Main Parse/Lexer.java $$n && \
	  ${JAVA} Main.Main Parse/Grm14.java $$n && \
	  ${JAVA} Main.Main tests/Escape.java) || exit 1; \
	done
	for n in 2 3 4 5; do \
	  ${JAVA} Main.Main tests/Eric.java $$n || exit 1; \
	done
	${JAVA} Main.Main tests/TestJSR201.java 5
	${JAVA} Main.Main tests/Test15.java 5
	${JAVA} Main.Main tests/Eric15.java 5
# always run the test.
phony:

# target to make the distributed files.
dist:
	-$(RM) -rf JavaGrammar javagrm.tar.gz javagrm.zip
	cvs -d `cat CVS/Root` co -A -P JavaGrammar
	find JavaGrammar -type d -name CVS | xargs $(RM) -rf
	tar czvf javagrm.tar.gz JavaGrammar
	zip -r javagrm.zip JavaGrammar
	cp javagrm.tar.gz `date +javagrm-%d-%b-%Y.tar.gz`
	cp README javagrm-README.txt
	$(RM) -rf JavaGrammar
upload: dist
	chmod a+r javagrm*
	scp javagrm* shades.cs.princeton.edu:/u/appel/public_html/modern/java/CUP

# clean up after ourselves.
clean:
	$(RM) Lex/Sym.java \
		$(foreach j,$(JAVA_GRAMMARS),Parse/Grm$(j).err) \
		$(foreach j,$(JAVA_GRAMMARS),Parse/Grm$(j).java) \
		$(foreach j,$(JAVA_GRAMMARS),Parse/Sym$(j).java) \
		Parse/parser.java Parse/sym.java \
              */*.class 

veryclean: clean
	$(RM) *~ */*~ javagrm*
