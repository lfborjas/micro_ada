procedure recordsTest is
 type Date is
  record
   Day   : Integer range 1 .. 31;
   Month : Integer range 1 .. 12;
   Year  : Integer range 1 .. 4000 := 1995;
  end record;
  Ada_Birthday : Date;
begin 
   Ada_Birthday.Month := 12;
   Ada_Birthday.Day   := 10;
   Ada_Birthday.Year  := 1815;
   --errores: 
   --querer acceder a un campo inexistente
   Ada_Birthday.Jahre := 2009;
   --querer asignar algo inválido:
   Ada_Birthday.Month := 12.5;
   --querer ir más allá:
   Ada_Birthday.Day.Day := 1;

end recordsTest;
