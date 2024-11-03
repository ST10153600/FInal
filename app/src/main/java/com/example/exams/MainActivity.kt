package com.example.exams

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.exams.fragments.HomeFragment
import com.example.exams.fragments.InsertTaskFragment
import com.example.exams.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the initial fragment
        loadFragment(HomeFragment())

        // Set up Bottom Navigation
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_search -> loadFragment(InsertTaskFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
                R.id.action_settings -> loadFragment(SettingsFragment())
                else -> false
            }
        }
    }

    // Function to replace fragments
    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        return true
    }
}
