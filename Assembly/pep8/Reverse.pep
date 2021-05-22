; This program reads two characters.
; First one is stored in a hard-coded memory location,
; and the second one is stored in 'char2' location.
; Then outputs the characters in reverse order.

         CHARI 0x0010, d
         CHARI char2, d
         CHARO char2, d
         CHARO 0x0010, d

         STOP
         
         ; Reserves two bytes of memory in location 'char2'
char2:   .BLOCK 2

         .END
