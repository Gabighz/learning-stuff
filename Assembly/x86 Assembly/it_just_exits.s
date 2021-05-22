#PURPOSE:  Simple program that exits and returns a
#          status code back to the Linux kernel
#

#INPUT:    none
#

#OUTPUT: returns a status code. This can be viewed
# 	 by typing 
#	 echo $?
#
#

#VARIABLES:
#	%eax holds the system call number
# 	%ebx holds the return status

.section .data # what starts with a dot
	       # is an assembler directive

.section .text
.globl _start 

_start:
	movl $1, %eax # this is the linux system call for exiting a program

	movl $0, %ebx # this is the status number to be returned

	int $0x80 # wakes up the kernel to run the exit command
