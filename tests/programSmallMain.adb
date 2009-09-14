-- Main routine to start up "Small", a small text adventure game to
-- demonstrate Ada 95.

--
-- Copyright (C) 1996 Ada Resource Association (ARA), Columbus, Ohio.
-- Author: David A. Wheeler
--

-- For documentation see the following URL:
--   http://www.adahome.com//Tutorials/Lovelace/small.htm


procedure Small is
  Command : Unbounded_String; -- Contains user's current command.
  Quit    : Boolean := False;
begin
 Put_Line("Welcome to a Small World!");

 World.Setup;

 while not Quit loop
  New_Line;
  Put_Line("Your Command?");
  Get_Line(Command);
  Parser.Execute(Command, Quit);
 end loop;

 Put_Line("Bye!");
end Small;

--
-- Permission to use, copy, modify, and distribute this software and its
-- documentation for any purpose and without fee is hereby granted,
-- provided that the above copyright and authorship notice appear in all
-- copies and that both that copyright notice and this permission notice
-- appear in supporting documentation.
-- 
-- The ARA makes no representations about the suitability of this software
-- for any purpose.  It is provided "as is" without express
-- or implied warranty.
-- 


