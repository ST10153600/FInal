package com.example.exams

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class Settings: AppCompatActivity() {

    private lateinit var languageSpinner: Spinner
    private lateinit var themeSwitch: Switch
    private lateinit var logoutButton: Button
    private lateinit var upgradeButton: Button
    private lateinit var auth: FirebaseAuth
    private var isInitialSpinnerSetup = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        languageSpinner = findViewById(R.id.languageSpinner)
        themeSwitch = findViewById(R.id.themeSwitch)
        logoutButton = findViewById(R.id.logoutButton)
        upgradeButton = findViewById(R.id.upgradeButton)

        auth = FirebaseAuth.getInstance()

        // Set up Language Change
        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                if (isInitialSpinnerSetup) {
                    isInitialSpinnerSetup = false
                } else {
                    // Apply language change only if not initial setup to avoid screen twerking
                    when (position) {
                        0 -> setLocale("en")
                        1 -> setLocale("zu")
                        2 -> setLocale("st")
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Set up Theme Switch
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Set up Logout
        logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        // Disable the upgrade button for now
        upgradeButton.isEnabled = false
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Restart activity to apply changes
        val refresh = Intent(this, Settings::class.java)
        startActivity(refresh)
        finish()
    }
}
