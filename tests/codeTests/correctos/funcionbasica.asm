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
	beq, $a2, 1, _L2
_L1:
	b _L3
_L2:
	add $t0, $a0, $a1
	move $v0, $t0
	b _exit_funcionbasica__operar
	b _L4
_L3:
	mul $t1, $a0, $a1
	move $v0, $t1
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
	move $v0, $t2
	li $v0, 5
	syscall
	move $v0, $t3
	move $a1, $t2
	move $a2, $t3
	li $a3, 1
_L6:
	jal _funcionbasica__operar
	move $t4, $v0
	li $v0, 1
	move $a0, $t4
	syscall
	move $a1, $t3
	move $a2, $t2
	li $a3, 2
_L7:
	jal _funcionbasica__operar
	move $t5, $v0
	li $v0, 1
	move $a0, $t5
	syscall
	li $v0, 10
	syscall


