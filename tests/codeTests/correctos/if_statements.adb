procedure Main is
a: integer:= 1;
begin
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
