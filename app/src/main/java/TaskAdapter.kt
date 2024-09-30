package com.example.exams

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val taskList: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Views for task details
        private val subjectTextView: TextView = itemView.findViewById(R.id.subjectText)
        private val assessmentTypeTextView: TextView = itemView.findViewById(R.id.assessmentTypeText)
        private val dueDateTextView: TextView = itemView.findViewById(R.id.dueDateText)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionText)
        private val countdownTextView: TextView = itemView.findViewById(R.id.countdownTextView) // TextView for the countdown

        fun bind(task: Task) {
            // Bind task details
            subjectTextView.text = task.subject
            assessmentTypeTextView.text = task.assessmentType
            dueDateTextView.text = task.dueDate
            descriptionTextView.text = task.description

            // Start countdown
            val timeRemaining = task.getTimeRemaining()
            if (timeRemaining > 0) {
                startCountdown(timeRemaining, countdownTextView)
            } else {
                countdownTextView.text = "Expired"
            }
        }

        //

        private fun startCountdown(timeInMillis: Long, countdownTextView: TextView) {
            object : CountDownTimer(timeInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val days = millisUntilFinished / (1000 * 60 * 60 * 24)
                    val hours = (millisUntilFinished / (1000 * 60 * 60)) % 24
                    val minutes = (millisUntilFinished / (1000 * 60)) % 60
                    val seconds = (millisUntilFinished / 1000) % 60

                    countdownTextView.text = String.format("%02d days %02d:%02d:%02d", days, hours, minutes, seconds)
                }

                override fun onFinish() {
                    countdownTextView.text = "Expired"
                }
            }.start()
        }
    }
}
