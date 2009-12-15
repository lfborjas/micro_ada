procedure funcionBasica is
x, y, z: integer;
function operar(a, b, op: integer) return integer is
begin 
	if op = 1 then
		return a+b;
	else
		return a*b;
	end if;	
end operar;

begin
	put("Escriba x: ");
	get(x);
	put("Escriba y: ");
	get(y);
	z:=operar(x,y,1);
	put("x+y= ");
	put(z);
	put("\n");
	z:=operar(y,x,2);
	put("x*y= ");
	put(z);
	put("\n");
end funcionBasica;

