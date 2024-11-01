LI R1 0 ; x
LI R2 0 ; y
LI R3 20 ; size
MULT R4 R3 R3 ; area
LI R5 19 ; max index
JEQ 56 R2 R3                   ; y loop "56" is first line out of y loop
JEQ 53 R1 R3                  ; x loop "54" is first line out of x loop
LI R0 0 ;edge detection                    ; edge detecition 47 is line of "make dark"
JEQ 47 R2 R0  ; y=0
JEQ 47 R2 R5  ; y=19
JEQ 47 R1 R0  ; x=0
JEQ 47 R1 R5  ; x=19
MULT R6 R2 R3 ; img_addr = y*size              ; interior pixel
ADD R6 R1 R6 ; img_addr = x + img_addr
LD R7 R6 ; pixel = load img_addr
JEQ 47 R7 R0  ; if pixel = 0
SUBI R0 R2 1 ; y-1                             ; neighborup
MULT R6 R0 R3 ; img_addr = (y-1) * size
ADD R6 R1 R6 ; img_addr = x + img_addr
LD R7 R6 ; neigbhorup = load img_addr
LI R0 0
JEQ 47 R7 R0  ; make dark if neighbor is dark
ADDI R0 R2 1 ; y+1                           ; neighbordown
MULT R6 R0 R3 ; img_addr = (y+1) * size
ADD R6 R1 R6 ; img_addr = x + img_addr
LD R7 R6 ; neigbhordown = load img_addr
LI R0 0
JEQ 47 R7 R0  ; make dark if neighbor is dark
SUBI R0 R1 1 ; x-1                           ; neighborleft
MULT R6 R2 R3 ; img_addr = y * size
ADD R6 R0 R6 ; img_addr = (x-1) + img_addr
LD R7 R6 ; neigbhorleft = load img_addr
LI R0 0
JEQ 47 R7 R0  ; make dark if neighbor is dark
ADDI R0 R1 1 ; x+1                            ; neighborright
MULT R6 R2 R3 ; img_addr = y * size
ADD R6 R0 R6 ; img_addr = (x+1) + img_addr
LD R7 R6 ; neigbhorup = load img_addr
LI R0 0
JEQ 47 R7 R0 ; make dark if neighbor is dark
MULT R6 R2 R3 ; img_addr = y*size                  ; make light
ADD R6 R1 R6 ; img_addr = x + img_addr
ADD R6 R4 R6 ; img_addr = area + img_addr
LI R0 255 
SD R0 R6 ; store 255 at address + area
ADDI R1 R1 1 ;x++
JUMP 6
MULT R6 R2 R3 ; img_addr = y*size              ; make dark
ADD R6 R1 R6 ; img_addr = x + img_addr
LI R0 0
SD R0 R6
ADDI R1 R1 1   ;                  ; end of x loop
JUMP 6
ADDI R2 R2 1 ;                  ; end of y loop
LI R1 0                         ; out of x loop, set x = 0 again
JUMP 5
END