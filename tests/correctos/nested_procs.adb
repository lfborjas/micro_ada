--caso de prueba 2: procedimientos anidados, parámetros in out y out, goto.

procedure lastex is
	X: float := 0.0;
        Y: constant boolean :=(true and then false ) or else (true xor true);	
	procedure p(n: in out Integer; useless: out boolean:=false) is
                type nullrecord is record null; end record;
		procedure show is
			procedure Quadratic_Equation
			   (A, B, C :     Float;   
			    R1, R2  : out Float;
			    Valid   : out Boolean)  is
				   Z : Float;
			   begin
			   	Z := B**2 - 4.0 * A * C;
				   if Z < 0.0 or A = 0.0 then
					      Valid := False;  
					      R1    := 0.0;
					      R2    := 0.0;
				   else
					      Valid := True;
					      R1    := (-B + Sqrt (Z)) / (2.0 * A);
					      R2    := (-B - Sqrt (Z)) / (2.0 * A);
				   end if;
			     end Quadratic_Equation;
 
		begin
			 <<Sort>>			 
		         for I in 1 .. N-1 loop
		 	        if A(I) > A(I+1) then
		        	     Exchange(A(I), A(I+1));
	             		     goto Sort;
		                end if;
		        end loop;
		end show;
	begin
		show;
	end p;
begin
	p(1);
end lastex;
