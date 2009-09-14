PROCEDURE pdi IS

   -- Maximum length of input line
   line_length : CONSTANT := 80;

   -- Position of last character on input line
   last        : natural;
   -- Flag indicating (if true) that a prompt for a user command must be output
   repeat      : Boolean := true;
    --Input line entered by user
   response    : string;

BEGIN -- pdi

   -- Determine input option from user
   WHILE repeat LOOP

      -- Assume no need to reprompt
      repeat := false;

      -- Prompt user and read response
      text_io.new_line;
      text_io.put_line 
         ("Enter ""n"" for normal input from file ""input.dat""");
      text_io.put_line 
         ("   or ""r"" to restart from file ""checkpoint"": ");
      text_io.get_line (item => response, last => last);

      -- Interpret user response
      IF (last = 1) THEN 
         IF (response(1) = 'n') THEN

            -- Take search parameters from "input.dat"
            numbers.search.process_normal_input.start;

         ELSIF (response(1) = 'r') THEN

            -- Begin search from checkpoint file "pdi.ckp"
            numbers.search.do_search.enter_start_search_from_checkpoint;

         ELSE

            -- Invalid user input (unrecognized character); must reprompt
            repeat := true;
            text_io.new_line;

         END IF;

      ELSE

         -- Invalid user input (>1 character entered); must reprompt
         repeat := true;
         text_io.new_line;

      END IF;

   END LOOP;

   --Begin monitoring keyboard for user request to enter commands
   numbers.search.synchronize.start_monitor;

END pdi;

