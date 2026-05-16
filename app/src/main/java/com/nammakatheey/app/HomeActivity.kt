package com.nammakatheey.app
import androidx.recyclerview.widget.LinearLayoutManager

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import android.widget.TextView

class HomeActivity : AppCompatActivity() {

    private lateinit var rvDistricts: RecyclerView
    private lateinit var adapter: DistrictAdapter
    private var districtList = mutableListOf<String>()
    private var filteredList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Set welcome message
        val prefs = getSharedPreferences("namma_kathey_prefs", MODE_PRIVATE)
        val userName = prefs.getString("user_name", "Explorer")
        val tvWelcome = findViewById<TextView>(R.id.tvWelcomeUser)
        tvWelcome.text = "Hello, $userName! 👋"

        // Setup district list
        setupDistrictList()

        // Setup RecyclerView
        rvDistricts = findViewById(R.id.rvDistricts)
        rvDistricts.layoutManager = LinearLayoutManager(this)
        adapter = DistrictAdapter(filteredList) { districtName ->
            // On district clicked — go to Hero List
            val intent = Intent(this, HeroListActivity::class.java)
            intent.putExtra("district_name", districtName)
            startActivity(intent)
        }
        rvDistricts.adapter = adapter

        // Search functionality
        val etSearch = findViewById<TextInputEditText>(R.id.etSearch)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterDistricts(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Language toggle
        val btnToggle = findViewById<com.google.android.material.button.MaterialButton>(R.id.btnToggleLanguage)
        btnToggle.setOnClickListener {
            val currentLang = prefs.getString("user_language", "English")
            val newLang = if (currentLang == "English") "Kannada" else "English"
            prefs.edit().putString("user_language", newLang).apply()
            btnToggle.text = if (newLang == "Kannada") "EN" else "ಕನ್ನಡ"
        }

        // Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun setupDistrictList() {
        districtList = mutableListOf(
            "Bagalkot", "Ballari", "Belagavi", "Bengaluru Rural",
            "Bengaluru Urban", "Bidar", "Chamarajanagar", "Chikkaballapura",
            "Chikkamagaluru", "Chitradurga", "Dakshina Kannada", "Davanagere",
            "Dharwad", "Gadag", "Hassan", "Haveri",
            "Kalaburagi", "Kodagu", "Kolar", "Koppal",
            "Mandya", "Mysuru", "Raichur", "Ramanagara",
            "Shivamogga", "Tumakuru", "Udupi", "Uttara Kannada",
            "Vijayapura", "Yadgir"
        )
        filteredList.addAll(districtList)
    }

    private fun filterDistricts(query: String) {
        filteredList.clear()
        if (query.isEmpty()) {
            filteredList.addAll(districtList)
        } else {
            filteredList.addAll(
                districtList.filter {
                    it.contains(query, ignoreCase = true)
                }
            )
        }
        adapter.notifyDataSetChanged()
    }
}