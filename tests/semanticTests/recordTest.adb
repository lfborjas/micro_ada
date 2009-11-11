procedure Main is
type Date is record
	day: Integer;
	month: Integer;
	year: Integer;
end record;
cumple: Date;
begin
	cumple.day:=6;
	cumple.month:=1;
	cumple.year:=1989;	
end Main;
