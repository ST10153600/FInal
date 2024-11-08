package com.example.exams

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var fullNameInput: TextInputEditText
    private lateinit var signUpEmailInput: TextInputEditText
    private lateinit var signUpPasswordInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText
    private lateinit var signInText: TextView
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        fullNameInput = findViewById(R.id.fullNameInput)
        signUpEmailInput = findViewById(R.id.signUpEmailInput)
        signUpPasswordInput = findViewById(R.id.signUpPasswordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        signInText = findViewById(R.id.signInText)
        signUpButton = findViewById(R.id.signUpButton)


        // Sign-Up button onClick listener
        signUpButton.setOnClickListener {
            val fullName = fullNameInput.text.toString()
            val email = signUpEmailInput.text.toString()
            val password = signUpPasswordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            if (validateInput(fullName, email, password, confirmPassword)) {
                createAccount(email, password)
            }
        }

        // Login text listener
         signInText.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }



    fun validateInput(fullName: String, email: String, password: String, confirmPassword: String): Boolean {
        return when {
            fullName.isEmpty() -> {
                fullNameInput.error = "Full Name is required"
                false
            }
            email.isEmpty() -> {
                signUpEmailInput.error = "Email is required"
                false
            }
            password.isEmpty() -> {
                signUpPasswordInput.error = "Password is required"
                false
            }
            confirmPassword.isEmpty() -> {
                confirmPasswordInput.error = "Please confirm your password"
                false
            }
            password != confirmPassword -> {
                confirmPasswordInput.error = "Passwords do not match"
                false
            }
            else -> true
        }
    }

    // If all credentials are valid
    // We use the function createUserWithEmailAndPassword.
    // Using the auth object, pass in the email address.

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign up success
                    val user: FirebaseUser? = auth.currentUser
                    Log.d("SignUpActivity", "createUserWithEmail:success")
                    Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show()

                    finish()
                } else {
                    // If sign up fails, display a message to the user
                    Log.w("SignUpActivity", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Sign up failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
