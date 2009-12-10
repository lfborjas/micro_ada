procedure Main is
a: Integer:=0;
begin
	while a<=0 
	loop
		if a mod 2 = 0 then
			a:=a-1;
		else
			a:=a-2;
		end if;
	end loop;
end Main;
