procedure lastex is

	procedure p(n: Integer) is

		procedure show is 
		begin
			if n>0 then p(n-1);
			end if;
			put(n);
			new_line;
		end show;
	begin
		show;
	end p;
begin
	p(1);
end lastex;
