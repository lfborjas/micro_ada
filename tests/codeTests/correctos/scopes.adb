procedure foobar is
a : integer;
type date is record
	day: integer:= 6;
	month: integer:=1;
	year:integer:= 1989;
end record;
	procedure sub is
		b: float:=0.0;
		procedure foobar is
		c: boolean:=true;
		d: date;
			begin
				if true then
					put("ich bin foobar jr :D");
				end if;				
			end foobar;
	begin
		loop
		put("ich bin die kleine foobar");
			exit when 1=1;
		end loop;
	end sub;
begin
	for i in 1..10 loop
		put("es ist wunderbar!");
	end loop;
end foobar;
