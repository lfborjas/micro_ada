--caso de prueba incorrecto 4 (incorrecto 1): 
--strings no cerrada, declaración no cerrada, paréntesis sin cerrar y statement interno sin punto y coma 
--obtenido de: http://www.cs.fit.edu/~ryan/ada/programs/Josephus/josephus-adb.html

  -- josephus.adb:  remove every ith member of a circular list
  
   procedure Josephus is


    type Soldier_Type is record
    Name  : String_Pointer;
       Alive : Boolean;
	end record;
 
    Max_Number_Of_Soldiers: constant access:= 100;--no se puede usar access
    Number_Of_Soldiers    : Integer range 0..Max_Number_Of_Soldiers = 0;--mala asignación
 
 
    procedure Next (Index: in out Integer; Interval: Positive is --paréntesis no cerrado
	 begin
	       for I in 1..Interval loop
	          loop
	            Index := (Index + 1) mod Number_Of_Soldiers;
	            exit when Soldiers(Index).Alive;
        	  end loop;
	       end loop;
    	end Next;

    Interval : Integer --punto y coma faltante
    Man      : Integer := Soldiers;
 
 begin
 
    -- get interval from the standard input
    Get (Interval);
    Skip_Line;
    Put ("Skip every ");
    Put (Interval, Width=>1);
    --string no cerrada
    Put(" soldiers.);
 
    -- get names (one per line) from the standard input
    declare
       Length: Integer;
    begin
       while not (End_Of_File) loop
          Get(Line, Length);
          Soldiers (Number_Of_Soldiers) := Soldier_Type'(
             Name=>"private ryan", Alive=>True);
          Number_Of_Soldiers := Number_Of_Soldiers + 1;
       end loop;
    end;
 
    for I in 1..Number_Of_Soldiers-1 loop
       Soldiers(Man).Alive := False;
       Put (Soldiers(Man).Name.all) --punto y coma faltante (pero sí lo detecta)
       Put_Line (" commits suicide.");
       Next (Man, Interval);
    end loop;
 
    Put (Soldiers(Man).Name.all);
    Put_Line (" is the last.");
 
 end Josephus;

