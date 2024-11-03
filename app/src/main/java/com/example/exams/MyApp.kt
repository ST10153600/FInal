package com.example.exams

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.*

class MyApp : Application() {

    fun applyLocale() {
        val preferences = getSharedPreferences("AppSettings", 0)
        val languageCode = preferences.getString("language", "en") ?: "en"

        val locale = when (languageCode) {
            "zu" -> Locale("zu")
            "sot" -> Locale("sot")
            else -> Locale("en")
        }

        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        createConfigurationContext(config)

        resources.updateConfiguration(config, resources.displayMetrics)
    }

    override fun onCreate() {
        super.onCreate()
        applyLocale()
    }
}