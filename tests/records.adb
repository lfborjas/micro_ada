
procedure Main is

 type Car is record
     Identity       : Long_Long_Integer;
     Number_Wheels  : Positive range 1 .. 10;
     Paint          : Color;
     Horse_Power_kW : Float range 0.0 .. 2_000.0;
     Consumption    : Float range 0.0 .. 100.0
  end record;

 BMW : Car :=
    (Identity       => 2007_752,
     Number_Wheels  => 5,
     Horse_Power_kW => 190.0,
     Consumption    => 10.1,
     Paint          => Blue);
begin


null;

end Main;
