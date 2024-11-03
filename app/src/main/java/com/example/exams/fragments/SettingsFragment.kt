package com.example.exams.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.exams.Login
import com.example.exams.MyApp
import com.example.exams.R
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class SettingsFragment : Fragment() {

    private lateinit var languageSpinner: Spinner
    private lateinit var themeSwitch: Switch
    private lateinit var logoutButton: Button
    private lateinit var upgradeButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var preferences: SharedPreferences
    private var isInitialSpinnerSetup = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        languageSpinner = view.findViewById(R.id.languageSpinner)
        themeSwitch = view.findViewById(R.id.themeSwitch)
        logoutButton = view.findViewById(R.id.logoutButton)
        upgradeButton = view.findViewById(R.id.upgradeButton)

        auth = FirebaseAuth.getInstance()
        preferences = requireActivity().getSharedPreferences("AppSettings", 0)

        // Initialize spinner with language array
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.languages_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            languageSpinner.adapter = adapter
        }

        // Load saved settings
        val isDarkMode = preferences.getBoolean("dark_mode", false)
        themeSwitch.isChecked = isDarkMode
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        // Set language spinner selection
        val savedLanguage = preferences.getString("language", "en") ?: "en"
        setLanguageSelection(savedLanguage)

        // Set spinner listener for language selection
        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!isInitialSpinnerSetup) {
                    val lang = when (position) {
                        0 -> "en"
                        1 -> "zu"
                        2 -> "sot"
                        else -> "en"
                    }
                    saveLanguagePreference(lang)
                    (requireActivity().application as MyApp).applyLocale()
                    requireActivity().recreate()
                } else isInitialSpinnerSetup = false
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Theme switch listener
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            val mode = if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
            preferences.edit().putBoolean("dark_mode", isChecked).apply()
        }

        // Logout button listener
        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(context, Login::class.java))
            activity?.finish()
        }

        upgradeButton.isEnabled = false

        return view
    }

    private fun saveLanguagePreference(languageCode: String) {
        preferences.edit().putString("language", languageCode).apply()
    }

    private fun setLanguageSelection(languageCode: String) {
        val position = when (languageCode) {
            "en" -> 0
            "zu" -> 1
            "sot" -> 2
            else -> 0
        }
        languageSpinner.setSelection(position)
    }
}