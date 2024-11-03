package com.example.exams

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class Home : AppCompatActivity() {

    //The following information was taken from ChatGPT and modified for the recycler view
    //Author: ChatGPT

    private lateinit var database: DatabaseReference
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private val taskList: MutableList<Task> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize Firebase Realtime Database reference
        database = FirebaseDatabase.getInstance().reference

        // Initialize RecyclerView
        taskRecyclerView = findViewById(R.id.taskRecyclerView)
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(taskList)
        taskRecyclerView.adapter = taskAdapter

        // Load sorted tasks from Firebase
        loadSortedTasks()

        // Floating Action Button to add a new task
        val addTaskButton: FloatingActionButton = findViewById(R.id.addTaskButton)
        addTaskButton.setOnClickListener {
            // Navigate to InsertTaskActivity when clicked
            val intent = Intent(this, InsertTask::class.java)
            startActivity(intent)
        }

        // Bottom Navigation Bar setup
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Already on home
                    true
                }
                R.id.nav_search -> {
                    // Show profile pop-up
                    Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_profile -> {
                    // Show profile pop-up
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_settings -> {
                    // Navigate to SettingsActivity
                    val intent = Intent(this, Settings::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    // Method to load tasks sorted by due date
    private fun loadSortedTasks() {
        database.child("tasks").orderByChild("dueDate").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                taskList.clear() // Clear the list before adding new tasks
                for (taskSnapshot in snapshot.children) {
                    val task = taskSnapshot.getValue(Task::class.java)
                    task?.let {
                        taskList.add(it) // Add each task to the list
                    }
                }
                taskAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TaskListActivity", "Failed to load tasks.", error.toException())
                Toast.makeText(this@Home, "Failed to load tasks", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
