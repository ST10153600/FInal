package com.example.exams.com.example.exams.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Task(
    val id: String? = null,
    val subject: String = "",
    val assessmentType: String = "",
    val dueDate: String = "",
    val description: String = ""
) {
    // Secondary constructor for Firebase
    constructor() : this("", "", "", "", "")

    fun getTimeRemaining(): Long {
        // Adjusted to match date and time if needed, e.g., "yyyy-MM-dd HH:mm:ss"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            val dueDateObj: Date? = dateFormat.parse(dueDate)
            val currentDate = Date()
            if (dueDateObj != null && dueDateObj.after(currentDate)) {
                dueDateObj.time - currentDate.time
            } else {
                0 // Return 0 if the date is in the past or null
            }
        } catch (e: Exception) {
            0 // Return 0 if parsing fails
        }
    }
}
