package com.nammakatheey.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Get user data from prefs
        val prefs = getSharedPreferences("namma_kathey_prefs", MODE_PRIVATE)
        val userName = prefs.getString("user_name", "Explorer") ?: "Explorer"
        val badges = prefs.getStringSet("badges", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        // Set user name
        findViewById<TextView>(R.id.tvProfileName).text = userName
        findViewById<TextView>(R.id.tvStoriesRead).text = "${badges.size} Stories Read"

        // Set profile initial letter
        val initial = userName.first().uppercaseChar().toString()
        findViewById<TextView>(R.id.tvProfileInitial).text = initial

        // Set stats
        findViewById<TextView>(R.id.tvTotalStories).text = badges.size.toString()
        findViewById<TextView>(R.id.tvTotalBadges).text = badges.size.toString()

        // Check if badge just earned
        val badgeEarned = intent.getStringExtra("badge_earned")
        val score = intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 3)

        if (badgeEarned != null) {
            val cardBadge = findViewById<MaterialCardView>(R.id.cardBadgeEarned)
            cardBadge.visibility = View.VISIBLE
            findViewById<TextView>(R.id.tvBadgeHeroName).text =
                "$badgeEarned — Score: $score/$total"
        }

        // Setup badges RecyclerView
        val rvBadges = findViewById<RecyclerView>(R.id.rvBadges)
        val llNoBadges = findViewById<LinearLayout>(R.id.llNoBadges)

        if (badges.isEmpty()) {
            llNoBadges.visibility = View.VISIBLE
            rvBadges.visibility = View.GONE
        } else {
            llNoBadges.visibility = View.GONE
            rvBadges.visibility = View.VISIBLE
            rvBadges.layoutManager = GridLayoutManager(this, 2)
            rvBadges.adapter = BadgeAdapter(badges.toList())
        }

        // Logout button
        findViewById<MaterialButton>(R.id.btnLogout).setOnClickListener {
            prefs.edit().clear().apply()
            val logoutIntent = Intent(this, OnboardingActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logoutIntent)
            finish()
        }
    }
}