package com.example.exams.com.example.exams.models

import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Exam(private val examDate: LocalDate) {

    // Function to calculate days until the exam
    fun daysUntilExam(currentDate: LocalDate = LocalDate.now()): Long {
        return if (currentDate.isBefore(examDate) || currentDate.isEqual(examDate)) {
            ChronoUnit.DAYS.between(currentDate, examDate)
        } else {
            0 // Return 0 if the exam date is in the past
        }
    }

    // Function to check if the exam is today
    fun isExamToday(currentDate: LocalDate = LocalDate.now()): Boolean {
        return currentDate.isEqual(examDate)
    }
}
