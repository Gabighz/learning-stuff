; This program asks the user for a pair of two numbers or zero to terminate it.
; If the user types 0, the program terminates.
; Otherwise, the program will calculate the sum and the difference of the input.
; It will keep doing all the above steps until the user types 0.

        ; The following 5 lines branch to memory location 'main', reserve two Words with value zero to store the sum and the difference,
        ; and reserve 2 Bytes to store the first number and the second number respectively.
        BR       main
sum:   .WORD     0x0000
dif:   .WORD     0x0000
num1:  .BLOCK 2
num2:  .BLOCK 2

         ; The following four lines reserve some memory to store some ASCII strings.
askin:   .ASCII    "\nEnter two numbers or zero to terminate:\n\x00"
outsum:  .ASCII    "Their sum is: \x00"
outdif:  .ASCII    "\nTheir difference is: \x00"
goodbye: .ASCII    "Goodbye!\x00"

end:     STRO    goodbye, d
         STOP

       ; The following ten lines calculate the sum.
       ; The program outputs a string, loads the sum into the accumulator,
       ; reads the first number of the pair, and it branches to memory location 'end' if the input is 0.
       ; If the input is not 0, it loads the first number into the accumulator,
       ; reads the second number of the pair and adds it contents to the accumulator,
       ; outputs a string, stores the contents of the accumulator in 'sum', and then outputs 'sum'.
main:  STRO      askin, d
       LDA       sum , d
       DECI      num1, d
       BREQ      end
       LDA       num1, d
       DECI      num2, d
       ADDA      num2, d
       STRO      outsum, d
       STA       sum, d
       DECO      sum, d


       ; The following six lines calculate the difference.
       ; The program loads the difference into the accumulator and the first number,
       ; it subtracts the content of the second number from the accumulator,
       ; outputs a string, stores the contents of the accumulator in 'dif', and then outputs 'dif'.
       LDA       dif, d
       LDA       num1, d
       SUBA      num2, d
       STRO      outdif, d
       STA       dif, d
       DECO      dif, d

       ; After the above calculations are done, the program branches to memory location 'main', creating a loop
       BR        main

       .END
