LI R1 0           ; x (NOTE THAT VSCODE IS NOT 0 INDEXED)
LI R2 0           ; y
LI R3 20          ; size
MULT R4 R3 R3     ; area
LI R5 19          ; max index
JEQ 57 R2 R3      ; y loop "56" is first line out of y loop (57 in vscode)
JEQ 54 R1 R3      ; x loop "54" is first line out of x loop (55 in vscode)
LI R0 0           ; edge detection, 47 is line of "make dark"
JEQ 47 R2 R0      ; y=0
JEQ 47 R2 R5      ; y=19
JEQ 47 R1 R0      ; x=0
JEQ 47 R1 R5      ; x=19
MULT R6 R2 R3     ; img_addr = y*size
ADD R6 R1 R6      ; img_addr = x + img_addr
LD R7 R6          ; pixel = load img_addr
JEQ 47 R7 R0      ; if pixel = 0
SUBI R0 R2 1      ; y-1, neighbor up
MULT R6 R0 R3     ; img_addr = (y-1) * size
ADD R6 R1 R6      ; img_addr = x + img_addr
LD R7 R6          ; neighbor up = load img_addr
LI R0 0
JEQ 47 R7 R0      ; make dark if neighbor is dark
ADDI R0 R2 1      ; y+1, neighbor down
MULT R6 R0 R3     ; img_addr = (y+1) * size
ADD R6 R1 R6      ; img_addr = x + img_addr
LD R7 R6          ; neighbor down = load img_addr
LI R0 0
JEQ 47 R7 R0      ; make dark if neighbor is dark
SUBI R0 R1 1      ; x-1, neighbor left
MULT R6 R2 R3     ; img_addr = y * size
ADD R6 R0 R6      ; img_addr = (x-1) + img_addr
LD R7 R6          ; neighbor left = load img_addr
LI R0 0
JEQ 47 R7 R0      ; make dark if neighbor is dark
ADDI R0 R1 1      ; x+1, neighbor right
MULT R6 R2 R3     ; img_addr = y * size
ADD R6 R0 R6      ; img_addr = (x+1) + img_addr
LD R7 R6          ; neighbor right = load img_addr
LI R0 0
JEQ 47 R7 R0      ; make dark if neighbor is dark
MULT R6 R2 R3     ; img_addr = y*size, make light
ADD R6 R1 R6      ; img_addr = x + img_addr
ADD R6 R4 R6      ; img_addr = area + img_addr
LI R0 255 
SD R0 R6          ; store 255 at address + area
ADDI R1 R1 1      ; x++
JUMP 6
MULT R6 R2 R3     ; img_addr = y*size, make dark
ADD R6 R1 R6      ; img_addr = x + img_addr
ADD R6 R4 R6      ; img_addr += 400
LI R0 0
SD R0 R6
ADDI R1 R1 1      ; end of x loop
JUMP 6
ADDI R2 R2 1      ; outside of x loop, this is "end of y loop"
LI R1 0           ; out of x loop, set x = 0 again
JUMP 5
END               ;
