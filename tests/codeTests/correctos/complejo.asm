	.data
_msg0: .asciiz "\n"
_msg1: .asciiz "\n"
_msg2: .asciiz "escriba 2 ó 3 :"
_msg3: .asciiz "la suma de las potencias escritas es:"
_msg4: .asciiz "desea continuar (1/0)?"
_msg5: .asciiz "adiós!\n"

	.text
	.globl main
_complejo__cuenta_regresiva:
	#PROLOGUE:
	sub $sp, $sp, 8
	sw $ra, ($sp)
	sw $fp, 4($sp)
	sub $fp, $sp, 0
	move $sp, $fp
	#BODY:
	li $t0, 1
_L1:
	bge $t0, 10, _L3
_L2:
	b _L4
_L3:
	li $v0, 1
	move $a0, $t0
	syscall
	sub $t0, $t0, 1
	b _L1
_L4:
	#EPILOGUE:
_exit_complejo__cuenta_regresiva:
	add $sp, $fp, 0
	lw $fp, 4($sp)
	lw $ra, ($sp)
	add $sp, $sp, 8
	jr $ra

_complejo__sumar_potencias:
	#PROLOGUE:
	sub $sp, $sp, 20
	sw $ra, ($sp)
	sw $fp, 4($sp)
	sub $fp, $sp, 12
	move $sp, $fp
	#BODY:
	li $t1, 0
	beq $a0, 2, _L7
_L6:
	b _L12
_L7:
	li $t2, 1
_L8:
	blt $t2, 10, _L10
_L9:
	b _L11
_L10:
	mul $t3, $t2, $t2
	li $v0, 1
	move $a0, $t3
	syscall
	li $v0, 4
	la $a0, _msg0
	syscall
	mul $t4, $t2, $t2
	add $t5, $t1, $t4
	add $t2, $t2, 1
	b _L8
_L11:
	move $v0, $t5
	b _exit_complejo__sumar_potencias
	b _L20
_L12:
	beq $a0, 3, _L14
_L13:
	b _L19
_L14:
	li $t6, 10
_L15:
	bgt $t6, 0, _L17
_L16:
	b _L18
_L17:
	mul $t7, $t6, $t6
	mul $t8, $t7, $t6
	li $v0, 1
	move $a0, $t8
	syscall
	li $v0, 4
	la $a0, _msg1
	syscall
	mul $t9, $t6, $t6
	mul $s0, $t9, $t6
	add $s1, $t5, $s0
	sub $s2, $t6, 1
	b _L15
_L18:
	move $v0, $s1
	b _exit_complejo__sumar_potencias
	b _L20
_L19:
	move $v0, $a0
	b _exit_complejo__sumar_potencias
_L20:
	#EPILOGUE:
_exit_complejo__sumar_potencias:
	add $sp, $fp, 12
	lw $fp, 4($sp)
	lw $ra, ($sp)
	add $sp, $sp, 8
	jr $ra

main:
_complejo:
	#PROLOGUE:
	sub $sp, $sp, 16
	sw $ra, ($sp)
	sw $fp, 4($sp)
	sub $fp, $sp, 8
	move $sp, $fp
	#BODY:
	li $s3, 1
_L22:
	beq $s3, 1, _L24
_L23:
	b _L27
_L24:
	li $v0, 4
	la $a0, _msg2
	syscall
	li $v0, 5
	syscall
	move $s4, $v0
	li $v0, 4
	la $a0, _msg3
	syscall
	move $a0, $s4
_L25:
	jal _complejo__sumar_potencias
	move $s5, $v0
_L26:
	jal _complejo__cuenta_regresiva
	li $v0, 4
	la $a0, _msg4
	syscall
	li $v0, 5
	syscall
	move $s3, $v0
	b _L22
_L27:
	li $v0, 4
	la $a0, _msg5
	syscall
	li $v0, 10
	syscall


