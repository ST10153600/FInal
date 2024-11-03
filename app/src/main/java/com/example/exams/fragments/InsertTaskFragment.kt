package com.example.exams.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.exams.R
import com.example.exams.com.example.exams.models.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertTaskFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var subjectInput: EditText
    private lateinit var assessmentTypeInput: EditText
    private lateinit var dueDateInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var submitTaskButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_insert_task, container, false)

        // Initialize Firebase Realtime Database
        database = FirebaseDatabase.getInstance().reference

        subjectInput = view.findViewById(R.id.subjectInput)
        assessmentTypeInput = view.findViewById(R.id.assessmentTypeInput)
        dueDateInput = view.findViewById(R.id.dueDateInput)
        descriptionInput = view.findViewById(R.id.descriptionInput)
        submitTaskButton = view.findViewById(R.id.submitTaskButton)

        submitTaskButton.setOnClickListener {
            val subject = subjectInput.text.toString()
            val assessmentType = assessmentTypeInput.text.toString()
            val dueDate = dueDateInput.text.toString()
            val description = descriptionInput.text.toString()

            if (validateInput(subject, assessmentType, dueDate, description)) {
                saveTaskToDatabase(subject, assessmentType, dueDate, description)
            }
        }
        return view
    }

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

    private fun saveTaskToDatabase(subject: String, assessmentType: String, dueDate: String, description: String) {
        val taskId = database.child("tasks").push().key
        val task = Task(taskId, subject, assessmentType, dueDate, description)

        taskId?.let {
            database.child("tasks").child(it).setValue(task)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Task added successfully!", Toast.LENGTH_SHORT).show()
                        clearFields()
                    } else {
                        Toast.makeText(context, "Failed to add task.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun clearFields() {
        subjectInput.text.clear()
        assessmentTypeInput.text.clear()
        dueDateInput.text.clear()
        descriptionInput.text.clear()
    }
}
