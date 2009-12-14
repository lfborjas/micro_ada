procedure Main is
--función correcta
function foo(bar: boolean; var: out integer) return integer is
begin
	var:=2*3;
	return var;
	--return 3;
end foo;

--función incorrecta:
function useless(lessuse: FLOAT) return boolean is
	var: boolean;
begin
	if false then
		return 1;
	elsif true then
		return true;
	end if;
end useless;


a: integer;
b: float;
begin
	--parámetros incorrectos:
	a := foo(1, 2);
	a := foo(true, 2, 3);
	--a := foo("hola");
	--parametros correctos, destino incorrecto:
	b := foo(true, a);
	--pruebas con put y get_
	put("hola mundo!");
	put(1.0);
	put(TruE);
	put(2);
	put("hola", 2);	
	get(a);
	get(b);
	return 0;	
end Main;


