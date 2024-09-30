package com.example.exams

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*
import java.time.LocalDate

class ExamTest {

    // The following code was sourced and modified from StackOverFlow.com

    @Test
    fun testDaysUntilExam_sameDay() {
        // Arrange
        val examDate = LocalDate.of(2024, 10, 1)
        val currentDate = LocalDate.of(2024, 10, 1)
        val structuredExamCountdown = Exam(examDate)

        // Act
        val daysRemaining = structuredExamCountdown.daysUntilExam(currentDate)

        // Assert
        assertEquals(0, daysRemaining)
    }

    @Test
    fun testDaysUntilExam_futureDate() {
        // Arrange
        // Future exam date
        val examDate = LocalDate.of(2024, 12, 1)

        // Today's date
        val currentDate = LocalDate.of(2024, 10, 1)
        val structuredExamCountdown = Exam(examDate)

        // Act
        val daysRemaining = structuredExamCountdown.daysUntilExam(currentDate)

        // Assert
        // Expected difference between dates
        assertEquals(61, daysRemaining)
    }

    @Test
    fun testDaysUntilExam_pastDate() {
        // Arrange
        val examDate = LocalDate.of(2024, 9, 1)
        val currentDate = LocalDate.of(2024, 10, 1)
        val structuredExamCountdown = Exam(examDate)

        // Act
        val daysRemaining = structuredExamCountdown.daysUntilExam(currentDate)

        // Assert
        // Negative means exam is in the past
        assertEquals(-30, daysRemaining)
    }

    @Test
    fun testDaysUntilExam_withMockito() {
        // Arrange
        val examDate = LocalDate.of(2024, 12, 1)
        val currentDate = LocalDate.of(2024, 10, 1)
        val mockStructuredExamCountdown = mock(Exam::class.java)
        `when`(mockStructuredExamCountdown.daysUntilExam(currentDate)).thenReturn(61)

        // Act
        val daysRemaining = mockStructuredExamCountdown.daysUntilExam(currentDate)

        // Assert
        assertEquals(61, daysRemaining)
        verify(mockStructuredExamCountdown).daysUntilExam(currentDate)
    }
}

