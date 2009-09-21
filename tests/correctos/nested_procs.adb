--caso de prueba 2: procedimientos anidados, parámetros in out y out, goto.

procedure lastex is
	X: float := 0.0;
        Y: constant boolean :=(true and then false ) or else (true xor true);	
	procedure p(n: in out Integer; useless: out boolean) is
                type nullrecord is record null; end record;
		procedure show is 
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
	p(1, False);
end lastex;
