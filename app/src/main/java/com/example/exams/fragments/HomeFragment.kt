package com.example.exams.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exams.R
import com.example.exams.com.example.exams.models.Task
import com.example.exams.com.example.exams.adapter.TaskAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private val taskList: MutableList<Task> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize Firebase Realtime Database reference
        database = FirebaseDatabase.getInstance().reference

        // Initialize RecyclerView
        taskRecyclerView = view.findViewById(R.id.taskRecyclerView)
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskAdapter = TaskAdapter(taskList)
        taskRecyclerView.adapter = taskAdapter

        // Load sorted tasks for the current user from Firebase
        loadSortedTasks()

        return view
    }

    private fun loadSortedTasks() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            Log.d("HomeFragment", "Loading tasks for user: $userId")

            // Query tasks for the current user
            database.child("tasks").child(userId).orderByChild("dueDate").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    taskList.clear()
                    Log.d("HomeFragment", "Tasks snapshot count: ${snapshot.childrenCount}")

                    for (taskSnapshot in snapshot.children) {
                        val task = taskSnapshot.getValue(Task::class.java)
                        task?.let { taskList.add(it) }
                    }

                    taskAdapter.notifyDataSetChanged()
                    Log.d("HomeFragment", "Tasks loaded: ${taskList.size}")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("HomeFragment", "Failed to load tasks.", error.toException())
                    if (isAdded) {
                        Toast.makeText(context, "Failed to load tasks", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.w("HomeFragment", "Fragment is not attached to context. Cannot show Toast.")
                    }
                }
            })
        } else {
            Log.d("HomeFragment", "No user is logged in.")
            Toast.makeText(context, "Please log in to view tasks.", Toast.LENGTH_SHORT).show()
        }
    }


}