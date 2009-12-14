procedure statementTest is
a: Integer;
	
begin
	a := 0;
	--ciclo correcto
	for i in 1..10
	loop
		a:= a+1;
	end loop;
	--ciclo incorrecto: rango inválido
	for i in 1..10.0
	loop null; end loop;
	--ciclo incorrecto: asignación de la variable esa:
	for j in 1..10
	loop j := 1; end loop;
	--while correcto:
	while True
	loop
		a := a+1;
	end loop;
	
	--if con elses correcto:
	if 3>5 and 5<6 then
		a:= 45;
	elsif False then
		a:= 54;
	else
		a := 100;
	end if;

	--if con elses incorrecto: condiciones inválidas
	if 4+5 then
		a:=45;
	elsif 4+5**6 then
		a:= 56;
	else
		a:= 4;
	end if;

end statementTest;
