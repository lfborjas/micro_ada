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
	get(x);
	get(y);
	z:=operar(x,y,1);
	put(z);
	z:=operar(y,x,2);
	put(z);
end funcionBasica;

