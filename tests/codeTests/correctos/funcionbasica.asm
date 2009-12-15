	.data
_msg0: .asciiz "escriba x: "
_msg1: .asciiz "escriba y: "
_msg2: .asciiz "x+y= "
_msg3: .asciiz "x*y= "
_msg4: .asciiz "\n"

	.text
	.globl main
_funcionbasica__operar:
	#PROLOGUE:
	sub $sp, $sp, 20
	sw $ra, ($sp)
	sw $fp, 4($sp)
	sub $fp, $sp, 12
	move $sp, $fp
	#BODY:
	beq $a2, 1, _L2
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
	add $sp, $sp, 8
	jr $ra

main:
_funcionbasica:
	#PROLOGUE:
	sub $sp, $sp, 20
	sw $ra, ($sp)
	sw $fp, 4($sp)
	sub $fp, $sp, 12
	move $sp, $fp
	#BODY:
	li $v0, 4
	la $a0, _msg0
	syscall
	li $v0, 5
	syscall
	move $t2, $v0
	li $v0, 4
	la $a0, _msg1
	syscall
	li $v0, 5
	syscall
	move $t3, $v0
	move $a0, $t2
	move $a1, $t3
	li $a2, 1
_L6:
	jal _funcionbasica__operar
	move $t4, $v0
	li $v0, 4
	la $a0, _msg2
	syscall
	li $v0, 1
	move $a0, $t4
	syscall
	move $a0, $t3
	move $a1, $t2
	li $a2, 2
_L7:
	jal _funcionbasica__operar
	move $t5, $v0
	li $v0, 4
	la $a0, _msg3
	syscall
	li $v0, 1
	move $a0, $t5
	syscall
	li $v0, 4
	la $a0, _msg4
	syscall
	li $v0, 10
	syscall


