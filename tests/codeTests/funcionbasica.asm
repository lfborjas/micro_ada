	.data

	.text
	.globl funcionbasica
main:
_funcionbasica__operar:
	#PROLOGUE:
	sub $sp, $sp, 20
	sw $ra, ($sp)
	sw $fp, 4($sp)
	sub $fp, $sp, 12
	move $sp, $fp
	#BODY:
	lw $s6, 8($fp)
	beq, $s6, 1, funcionbasica__operar
_L1:
	b _funcionbasica__operar
_L2:
	lw $t5, 0($fp)
	lw $t5, 4($fp)
	add $t5, $t5, $t5
	move $v0, $t5
	b _exit_funcionbasica__operar
	b _funcionbasica__operar
_L3:
	lw $s5, 0($fp)
	lw $s5, 4($fp)
	mul $s5, $s5, $s5
	move $v0, $s5
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
	li $v0, null
	syscall
	move $v0, $t4
	li $v0, null
	syscall
	move $v0, $s4
	move $a1, $t4
	move $a2, $s4
	li $a3, 1
_L6:
	jal _operar
	move $t7, $v0
	li $v0, 1
	la $a0, $t7
	syscall
	move $a1, $s4
	move $a2, $t4
	li $a3, 2
_L7:
	jal _operar
	move $s3, $v0
	li $v0, 1
	la $a0, $s3
	syscall
	li $v0, 10
	syscall


