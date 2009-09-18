--caso de prueba 6 (incorrecto 3)
--subprograma no cerrado, advertencias numéricas y de operadores
--expresiones mal escritas, tratar de usar cosas de arreglos
--sacado de http://www.cs.fit.edu/~ryan/ada/programs/recurse/binary_search-adb.html

   -- binary_search.adb:  recursive search for an element in a sorted array
   
   procedure Binary_Search is
   
      --type Array_Type is array (Positive range <>) of Integer;
     RLIMIT: constant integer:= 1;--para listas que empiezan en 1 (sí, yo sé, no funcionará) 	
     procedure Search (List    : in Array_Type;
                       Value   : in Integer;
                       Location: out Natural) is
        Midpoint: integer range 1..100000;
     begin
        if List.Length = 0 || List.Length==RLIMIT then
           Location := 0;
        else{--talvez se quiera delimitar algo...
           Midpoint := (List.First + List.Last) / (2E5/1E5);
           if List (Midpoint) = Value then
              Location := Midpoint;
           elsif List (Midpoint) > Value then
              Search (List (List.First..Midpoint-1), Value, Location);
           else--un programador bastante C:
              Search (List (Midpoint+1..List.Last), &Value, Location);
          } end if;
        end if;
     end Search;
     --no se aceptan arreglos:
     Data: constant Array_Type := [1,2,3,4,6,7,8,9];
     I   : Integer;
  
  begin
     Search (List=>Data, Value=>6, Location=>I);
     if I = 0 || I=RLIMIT then
        Put("Didn't find it.");
     elsif I != 0 || I != RLIMIT  then
        Put("Found it at location " & Integerlib.Image(I));
     end if;
     put("Bye");
  --end Binary_Search;

