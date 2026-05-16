package com.nammakatheey.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class OnboardingActivity : AppCompatActivity() {

    private var selectedLanguage = "English" // Default language

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // Get references to views
        val etName = findViewById<TextInputEditText>(R.id.etName)
        val btnKannada = findViewById<MaterialButton>(R.id.btnKannada)
        val btnEnglish = findViewById<MaterialButton>(R.id.btnEnglish)
        val btnLetsGo = findViewById<MaterialButton>(R.id.btnLetsGo)

        // Kannada button click
        btnKannada.setOnClickListener {
            selectedLanguage = "Kannada"
            // Highlight Kannada selected
            btnKannada.backgroundTintList = getColorStateList(R.color.deep_blue)
            btnKannada.setTextColor(getColor(R.color.white))
            // Reset English button
            btnEnglish.backgroundTintList = getColorStateList(R.color.white)
            btnEnglish.setTextColor(getColor(R.color.saffron))
        }

        // English button click
        btnEnglish.setOnClickListener {
            selectedLanguage = "English"
            // Highlight English selected
            btnEnglish.backgroundTintList = getColorStateList(R.color.deep_blue)
            btnEnglish.setTextColor(getColor(R.color.white))
            // Reset Kannada button
            btnKannada.backgroundTintList = getColorStateList(R.color.saffron)
            btnKannada.setTextColor(getColor(R.color.white))
        }

        // Let's Go button click
        btnLetsGo.setOnClickListener {
            val name = etName.text.toString().trim()

            // Validate name
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save name and language to SharedPreferences
            val prefs = getSharedPreferences("namma_kathey_prefs", MODE_PRIVATE)
            prefs.edit()
                .putString("user_name", name)
                .putString("user_language", selectedLanguage)
                .apply()

            // Go to Home Screen
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}