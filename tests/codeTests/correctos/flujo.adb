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

	if a > 10 then
		put("mayor que diez");
	end if;

	if a < 9 then
		put("menor que nueve");
	else
		a:=a+1;
	end if;

	if a=0 then
		a:= 1;
	elsif a=1 then
		a:=2;
	elsif a=2 then
		a:=3;
	else
		a:=4;
	end if;

	if a /= 0 then
		a:=5;
	elsif a<0 then
		a:=6;
	end if;

	if a > 1 then
		a:=7;
		a:=8;
	elsif a < 1 then
		a:=9;
	elsif a = 1 then
		a:=10;
	end if;
end Main;
