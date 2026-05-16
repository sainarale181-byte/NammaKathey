package com.nammakatheey.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Wait 2.5 seconds then go to Onboarding
        Handler(Looper.getMainLooper()).postDelayed({
            goToNextScreen()
        }, 2500)
    }

    private fun goToNextScreen() {
        // Check if user has already onboarded before
        val prefs = getSharedPreferences("namma_kathey_prefs", MODE_PRIVATE)
        val userName = prefs.getString("user_name", null)

        if (userName != null) {
            // User already set up — go to Home
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else {
            // First time user — go to Onboarding
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
        }
        finish()
    }
}