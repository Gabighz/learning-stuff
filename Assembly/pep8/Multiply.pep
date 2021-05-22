; This program asks the user for two decimal numbers and multiplies them.

         ; branch to memory location 'main'
         BR       main

         ; Generate a word with the 0 value in it.
         ; Will contain the result of the multiplication.
mult:     .WORD 0x0000

         ; Generate 2 bytes of storage for each location
         ; and set each byte to 0
num1:    .BLOCK 2
num2:    .BLOCK 2

         ; Reads a decimal number for each location
         ; and stores it
main:    DECI num1, d
         DECI num2, d

         ; The following lines multiply a number num1 by num2 number of times.
         
         ; Loads num2 into the accumulator
         ; and it branches to memory location 'done' if the input is 0.
loop:    LDA num2, d
         BREQ done

         ; Loads the contents of 'mult' into the accumulator
         ; and adds the contents of 'num1' to the accumulator.
         ; Finally, the contents of the accumulator are stored in 'mult'.
         LDA mult, d
         ADDA num1, d
         STA mult, d

         ; Loads 'num2' into the accumulator and subtracts 1 from it.
         ; Then stores the results of the accumulator in 'num2'.
         LDA num2, d
         SUBA 0x0001, i
         STA num2, d

         ; After the above calculations are done
         ; branch back to 'loop'
         BR loop

         ; If we get here, then the multiplication has been performed.
         ; So we output the contents of 'mult'.
done:    DECO mult, d

         STOP
         .END
