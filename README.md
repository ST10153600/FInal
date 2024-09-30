[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/LJtTzPxQ)
# Structured Exams

## Overview

*Structured Exams* is an Android application designed to help students efficiently manage their exam schedules and study plans. With features such as countdown timers, progress tracking, and user-friendly task management, the app aims to reduce stress and improve time management for students. Users can create personalized profiles, set reminders for upcoming exams, and stay organized throughout their academic journey.

## Features

- *User Management*:
  - *User Registration*: Users can register by providing their username, email, and password.
  - *User Login*: Secure login using email and password to access the user profile.
  - *Profile Retrieval*: Users can retrieve their profile details, including username and email, using their unique user ID.

- *Countdown Management*:
  - *Create Countdown*: Users can create countdowns for multiple exams by providing exam details such as the exam name, date, and priority level.
  - *Retrieve Countdown List*: Users can retrieve a list of all their countdowns, including details like exam names, dates, and priorities.
  - *Update Countdown*: Users can modify the details of an existing countdown, such as changing the exam date or priority level.
  - *Delete Countdown*: Users can delete a countdown that is no longer needed, with a confirmation message indicating successful deletion.

- *Progress Tracking*:
  - *Add/Update Progress*: Users can record or update their study progress for each exam countdown, including providing a percentage of completion.
  - *Retrieve Progress Records*: Users can retrieve all progress records associated with their countdowns, including the progress percentages.

- *Notifications*:
  - *Send Notification*: The system sends notifications to users about upcoming exams and study sessions, including a message and the date/time when the notification is scheduled.
  - *Retrieve Notifications*: Users can retrieve a list of all notifications sent to them, including message content and scheduled dates/times.

## Technologies Used

- *Android*: The application is built using Kotlin for native Android development.
- *Firebase*: Utilized for user authentication and data management.
- *Retrofit*: Used for seamless integration with the RESTful API.
- *Glide*: Integrated for image loading and handling.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/IIEWFL/opsc7312-part-2-techpulse.git
