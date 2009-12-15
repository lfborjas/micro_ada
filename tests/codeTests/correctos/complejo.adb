procedure complejo is
	procedure cuenta_regresiva is
	begin
		for i in reverse 10..1 loop
			put(i);
		end loop;
	end cuenta_regresiva;
	
	function sumar_potencias(power:integer) return integer is
	acum: integer :=0;
	j: integer;
	begin
		if power=2 then
			for i in 1..10 loop
				put(i*i);
				put("\n");
				acum:=acum+i*i;
			end loop;
			return acum;
		elsif power=3 then
			j := 10;
			while(j > 0) loop
				put(j*j*j);
				put("\n");
				acum:=acum+j*j*j;
				j:=j-1;
			end loop;
			return acum;
		else
			return power;
		end if;
	end sumar_potencias;
continuar: integer := 1;
prompt: integer;
begin
	
	while continuar=1 loop
		put("Escriba 2 ó 3 :");
		get(prompt);
		put("La suma de las potencias escritas es:");
		put(sumar_potencias(prompt));
		cuenta_regresiva;		
		put("Desea continuar (1/0)?");
		get(continuar);
	end loop;
	put("adiós!\n");
end complejo;
