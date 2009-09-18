--caso de prueba 5 (incorrecto 2)
--pone llamadas a funciones en la parte declarativa, errores dentro de la declaración de un record, loop no cerrado
--y subprograma con nombres incoherentes
--fuente: http://www.cs.fit.edu/~ryan/ada/programs/for/pythagorean-adb.html
 -- pythagorean.adb:  search for pythagorean triples up to N
   -- A pythagorean triple (A,B,C) has the property that A**2+B**2 = C**2.
  -- Note:  if (A,B,C) is a pythagorean triple, then so is (B,A,C).
   -- Also:  there are no pythagorean triples of the form (A,A,C).
  procedure Pythagorean is
     type Argument is record
	arg: Integer
	none:INTEger;
     end record;
    	
   --procedure con nombres incoherentes:
     procedure New_Line is
		begin 
			PUT("\n"); 
		end newline;

   --función con nombres de inicio y fin incoherentes:   
     function getArgument(useless:in out boolean) return Integer is
	Argumento: Argument;
	begin
		Argument.arg:=GET("introduzca el argumento");
		Argument.none:=16#FF.0A#E2;
		if useless then
			return Argument;
		else
			return 1;
		end if;
        end get_argument;--se puso un nombre incoherente

     Arg: constant Integer:= getArgument(false);
     N  : Integer;   -- (from the user) maximum number allowed in triple
     L  : BOOLEAN;  -- last postion in string (ignored)
  
  begin
  
     -- Convert the first command line argument (a string) to an integer
     Get (From=>Arg, Item=>N, Last=>L);
  
     -- Since a**2+b**2=b**2+a**2, we might as well assume a<b
     -- (a /= b since 2*a**2=c**2 is impossible for whole numbers),
     -- since a,b<c, we begin searching at b+1.
  
     for A in 1 .. N loop
        for B in A+1 .. N loop
           for C in B+1 .. N loop
              if (A**2+B**2 = C**2) then
                 -- established (A,B,C) is a pythagorean triple
                 Put (A, Width=>0);
                 Put (", ");
                 Put (B, Width=>0);
                 Put (", ");
                 Put (C, Width=>0);
                 New_Line;
              end if;
           end loop;
        end loop;
     --end loop;
  
  end Pythagorean;

