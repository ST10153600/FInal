package com.example.exams

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InsertTask : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var subjectInput: EditText
    private lateinit var assessmentTypeInput: EditText
    private lateinit var dueDateInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var submitTaskButton: Button

    //Insert data into Firebase Realtime Database was taken from Youtube
    //Author: CodingWithMitch


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_task)

        // Initialize Firebase Realtime Database
        database = FirebaseDatabase.getInstance().reference

        subjectInput = findViewById(R.id.subjectInput)
        assessmentTypeInput = findViewById(R.id.assessmentTypeInput)
        dueDateInput = findViewById(R.id.dueDateInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        submitTaskButton = findViewById(R.id.submitTaskButton)

        // Submit task button onClick listener
        submitTaskButton.setOnClickListener {
            val subject = subjectInput.text.toString()
            val assessmentType = assessmentTypeInput.text.toString()
            val dueDate = dueDateInput.text.toString()
            val description = descriptionInput.text.toString()

            if (validateInput(subject, assessmentType, dueDate, description)) {
                saveTaskToDatabase(subject, assessmentType, dueDate, description)
            }
        }

        // Bottom Navigation Bar setup
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_search -> {
                    // Stay on the current screen
                    true
                }
                R.id.nav_profile -> {
                    // Show a pop-up
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    // Validate input fields
    private fun validateInput(subject: String, assessmentType: String, dueDate: String, description: String): Boolean {
        return when {
            subject.isEmpty() -> {
                subjectInput.error = "Subject is required"
                false
            }
            assessmentType.isEmpty() -> {
                assessmentTypeInput.error = "Assessment Type is required"
                false
            }
            dueDate.isEmpty() -> {
                dueDateInput.error = "Due Date is required"
                false
            }
            description.isEmpty() -> {
                descriptionInput.error = "Description is required"
                false
            }
            else -> true
        }
    }

    // Save task to Firebase Realtime Database
    private fun saveTaskToDatabase(subject: String, assessmentType: String, dueDate: String, description: String) {
        val taskId = database.child("tasks").push().key // Create a unique key for the task
        val task = Task(taskId, subject, assessmentType, dueDate, description)

        taskId?.let {
            database.child("tasks").child(it).setValue(task)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Task added successfully!", Toast.LENGTH_SHORT).show()
                        clearFields()
                    } else {
                        Toast.makeText(this, "Failed to add task.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    // Clear input fields after submission
    private fun clearFields() {
        subjectInput.text.clear()
        assessmentTypeInput.text.clear()
        dueDateInput.text.clear()
        descriptionInput.text.clear()
    }
}

data class Task(
    val id: String?,
    val subject: String,
    val assessmentType: String,
    val dueDate: String,
    val description: String
) {
    fun getTimeRemaining(): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            val dueDateObj = dateFormat.parse(dueDate)
            val currentDate = Date()
            dueDateObj?.time?.minus(currentDate.time) ?: 0
        } catch (e: Exception) {
            0
        }
    }
}
