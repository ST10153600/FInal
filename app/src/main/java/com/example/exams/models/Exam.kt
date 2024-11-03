package com.example.exams.com.example.exams.models

import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Exam(private val examDate: LocalDate) {

    // Function to calculate days until the exam
    fun daysUntilExam(currentDate: LocalDate): Long {
        return ChronoUnit.DAYS.between(currentDate, examDate)
    }
}
