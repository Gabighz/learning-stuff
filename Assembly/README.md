# Assembly
Stuff in Assembly (with the PEP/8 virtual machine and x86 ISA)

More information about the PEP/8 simulator and assembler can be viewed [here](https://en.wikipedia.org/wiki/Pep/7). The names of the programs are self-explanatory. `Addition and substration` and `Collatz conjecture` also contain diagrams to better understand what the programs are doing.

To run the programs that target the x86 ISA on a Linux machine with the standard GCC toolset:
```
as name_program.s -o name_program.o
ld name_program.o -o name_program
./name_program
```

And to see the return code:
```
echo $?
```
