package com.example.exams


import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


// the code below was sourced and modified from blackboxAI
// Author: blackboxAI

class SignUpTest {

    private lateinit var signUpActivity: SignUp

    @Before
    fun setUp() {
        signUpActivity = SignUp()
    }

    @Test
    fun testValidateInput_Successful() {
        val isValid = signUpActivity.validateInput(
            "John Doe", "john@example.com", "password123", "password123"
        )
        assertTrue(isValid)
    }

    @Test
    fun testValidateInput_Failure_EmptyFields() {
        val isValid = signUpActivity.validateInput(
            "", "john@example.com", "password123", "password123"
        )
        assertFalse(isValid)
    }

    @Test
    fun testValidateInput_Failure_PasswordMismatch() {
        val isValid = signUpActivity.validateInput(
            "John Doe", "john@example.com", "password123", "differentpassword"
        )
        assertFalse(isValid)
    }
}