procedure Main is
--asignación a tipo inexistente:
a: lalo;
--asignación a variable:
b: a;
--asignación a función
function x(dummy: integer) return integer is begin return 0; end;
--c: x(1); TRUENA: entra a parameter_declaration!
--inicializaciones incorrectas:
d: Integer := true;
e: Float := 1+2*3-4;
f: Boolean := 5*4;
--correctas:
g: Integer := 1+6**5;
h: Boolean := 5<6;
begin
	d:= a + g;
end Main;
