	.data
_msg0: .asciiz "hola mundo!"

	.text
	.globl main
main:
_L0:
	#PROLOGUE:
	sub $sp, $sp, 8
	sw $ra, ($sp)
	sw $fp, 4($sp)
	sub $fp, $sp, 0
	move $sp, $fp
	#BODY:
	li $v0, 4
	la $a0, _msg0
	syscall
	li $v0, 10
	syscall


