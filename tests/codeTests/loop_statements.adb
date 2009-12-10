procedure Main is
a: Integer:=5;
begin
	while a<=0 
	loop
		if a mod 2 = 0 then
			a:=a-1;
		else
			a:=a-2;
		end if;
	end loop;

	for i in reverse 1..10
	loop
		if a mod 3 = 0 then
			a:=a+3;
		else
			a:=a-3;
		end if;
	end loop;
	
	for i in a..a-5
	loop
		put("nunca me debí haber ejecutado, pero nadie me puede detectar!");
	end loop;

	for i in 5..1
	loop
		put("nunca me ejecutaré!");
	end loop;

	loop
		if a<0 then
			exit;
		end if;
		put("hola mundo");
		a:=a+1;
	exit when a>10;
	end loop;

end Main;
