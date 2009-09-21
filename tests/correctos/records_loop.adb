--caso de prueba 3: records, loop normal
--sacado de: http://www.dwheeler.com/lovelace/s6s6.htm
procedure Main is
--declaramos el record:
	type Date is
	  record
	   Day   : Integer range 1 .. 31;
	   Month : Integer range 1 .. 12;
	   Year  : Integer range 1 .. 4000 := 1995;
	  end record;

--declaramos otra variable:
	Prompt: Integer := 0;
--ahora, otro record
	type Complex is
	  record
	    Real_Part, Imaginary_Part : Float := 0.0;
	  end record;
--ahora un procedure  
	procedure Demo_Date is  
 	   Ada_Birthday : Date;
           begin
	  	 Ada_Birthday.Month := 12;
		   Ada_Birthday.Day   := 10;
		   Ada_Birthday.Year  := 1815;
	   end Demo_Date;
--ahora, otras variables:
	ComplexPrompt: Complex;
	rPrompt: Float:=0.0;
	iPrompt: Float:=0.0;
--otra función:
	function getPrompt(r,i:Float) return Complex is
		retVal: Complex;
		begin						
			retVal.Real_part:= get("escriba algún número...");  
			iPrompt.Imaginary_Part:= get("escriba algún otro número");		
			return retVal;
		end;
begin --Main
	loop
		ComplexPrompt:= getPrompt(rPrompt, iPrompt);
		put(getPrompt);
		exit when ComplexPrompt.Real_Part>=0 and then ComplexPrompt.Imaginary_Part /= -1;
	end loop;
end Main;
