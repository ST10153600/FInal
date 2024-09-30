package com.example.exams

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
        holder.subjectText.text = task.subject
        holder.assessmentTypeText.text = task.assessmentType
        holder.dueDateText.text = task.dueDate
        holder.descriptionText.text = task.description
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subjectText: TextView = itemView.findViewById(R.id.subjectText)
        val assessmentTypeText: TextView = itemView.findViewById(R.id.assessmentTypeText)
        val dueDateText: TextView = itemView.findViewById(R.id.dueDateText)
        val descriptionText: TextView = itemView.findViewById(R.id.descriptionText)
    }
}
