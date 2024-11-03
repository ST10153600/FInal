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
    //I added this secondary constructor to handle no-argument scenario needed by Firebase
    constructor() : this("", "", "", "", "")

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