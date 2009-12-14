	.data

	.text
	.globl main
main:
_funcionbasica__operar:
	#PROLOGUE:
	sub $sp, $sp, 20
	sw $ra, ($sp)
	sw $fp, 4($sp)
	sub $fp, $sp, 12
	move $sp, $fp
	#BODY:
	ERROR $t0, $a2
	beq, $t0, 1, _L2
_L1:
	b _L3
_L2:
	ERROR $t2, $a0
	ERROR $t1, $a1
	add $t3, $t2, $t1
	move $v0, $t3
	b _exit_funcionbasica__operar
	b _L4
_L3:
	mul $t4, $t2, $t1
	move $v0, $t4
	b _exit_funcionbasica__operar
_L4:
	#EPILOGUE:
_exit_funcionbasica__operar:
	add $sp, $fp, 12
	lw $fp, 4($sp)
	lw $ra, ($sp)
	jr $ra

_funcionbasica:
	#PROLOGUE:
	sub $sp, $sp, 20
	sw $ra, ($sp)
	sw $fp, 4($sp)
	sub $fp, $sp, 12
	move $sp, $fp
	#BODY:
	li $v0, 5
	syscall
	move $v0, $t5
	li $v0, 5
	syscall
	move $v0, $t6
	move $a1, $t5
	move $a2, $t6
	li $a3, 1
_L6:
	jal _operar
	move $t7, $v0
	li $v0, 1
	la $a0, $t7
	syscall
	move $a1, $t6
	move $a2, $t5
	li $a3, 2
_L7:
	jal _operar
	move $t8, $v0
	li $v0, 1
	la $a0, $t8
	syscall
	li $v0, 10
	syscall


