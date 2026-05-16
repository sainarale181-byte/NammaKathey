package com.nammakatheey.app
import com.nammakatheey.app.data.model.Hero

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HeroListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_list)

        // Get district name passed from HomeActivity
        val districtName = intent.getStringExtra("district_name") ?: "Karnataka"

        // Set header title
        val tvTitle = findViewById<TextView>(R.id.tvDistrictTitle)
        tvTitle.text = "Heroes of $districtName"

        // Back button
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Get heroes for this district
        val heroes = getHeroesForDistrict(districtName)

        // Setup RecyclerView
        val rvHeroes = findViewById<RecyclerView>(R.id.rvHeroes)
        rvHeroes.layoutManager = LinearLayoutManager(this)
        val adapter = HeroAdapter(heroes) { hero ->
            val intent = Intent(this, StoryActivity::class.java)
            intent.putExtra("hero_name", hero.name)
            intent.putExtra("hero_district", districtName)
            startActivity(intent)
        }
        rvHeroes.adapter = adapter
    }

    private fun getHeroesForDistrict(district: String): List<Hero> {
        val allHeroes = mapOf(
            "Mysuru" to listOf(
                Hero("Tipu Sultan", "Freedom Fighter", "1750 - 1799", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Tipu_Sultan.jpg/330px-Tipu_Sultan.jpg"),
                Hero("Kuvempu", "Poet & Writer", "1904 - 1994", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Kuvempu.jpg/330px-Kuvempu.jpg"),
                Hero("Krishnaraja Wadiyar IV", "Social Reformer", "1884 - 1940", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Krishnaraja_Wadiyar_IV.jpg/330px-Krishnaraja_Wadiyar_IV.jpg")
            ),
            "Belagavi" to listOf(
                Hero("Kittur Chennamma", "Freedom Fighter", "1778 - 1829", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d2/Kittur_Chennamma.jpg/330px-Kittur_Chennamma.jpg"),
                Hero("Sangolli Rayanna", "Freedom Fighter", "1798 - 1831", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Sangolli_Rayanna.jpg/330px-Sangolli_Rayanna.jpg")
            ),
            "Shivamogga" to listOf(
                Hero("Keladi Chennamma", "Queen & Warrior", "1671 - 1696", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Keladi_Chennamma.jpg/330px-Keladi_Chennamma.jpg"),
                Hero("Kuvempu", "Poet & Writer", "1904 - 1994", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Kuvempu.jpg/330px-Kuvempu.jpg")
            ),
            "Chitradurga" to listOf(
                Hero("Onake Obavva", "Warrior Woman", "18th Century", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Onake_Obavva.jpg/330px-Onake_Obavva.jpg"),
                Hero("Madakari Nayaka", "King & Warrior", "1758 - 1779", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Madakari_Nayaka.jpg/330px-Madakari_Nayaka.jpg")
            ),
            "Dakshina Kannada" to listOf(
                Hero("Kota Shivaram Karanth", "Writer", "1902 - 1997", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Shivaram_Karanth.jpg/330px-Shivaram_Karanth.jpg"),
                Hero("U.R. Ananthamurthy", "Writer & Thinker", "1932 - 2014", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/UR_Ananthamurthy.jpg/330px-UR_Ananthamurthy.jpg")
            ),
            "Dharwad" to listOf(
                Hero("Basavanna", "Social Reformer", "1131 - 1167", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Basavanna.jpg/330px-Basavanna.jpg"),
                Hero("Gangubai Hangal", "Classical Singer", "1913 - 2009", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Gangubai_Hangal.jpg/330px-Gangubai_Hangal.jpg")
            ),
            "Bengaluru Urban" to listOf(
                Hero("Kempe Gowda I", "Founder of Bengaluru", "1510 - 1569", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Kempegowda_I.jpg/330px-Kempegowda_I.jpg"),
                Hero("Sir M. Visvesvaraya", "Engineer & Statesman", "1861 - 1962", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Sir_MV.jpg/330px-Sir_MV.jpg"),
                Hero("Dodda Kempe Gowda", "Administrator", "16th Century", imageUrl = "")
            ),
            "Kalaburagi" to listOf(
                Hero("Basavanna", "Social Reformer", "1131 - 1167", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Basavanna.jpg/330px-Basavanna.jpg"),
                Hero("Sharif Saheb", "Saint & Poet", "1819 - 1888", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Shishunala_Sharifa.jpg/330px-Shishunala_Sharifa.jpg")
            ),
            "Ballari" to listOf(
                Hero("Onake Obavva", "Warrior Woman", "18th Century", imageUrl = ""),
                Hero("Kampanna Udaiyar", "King & Warrior", "14th Century", imageUrl = "")
            ),
            "Hassan" to listOf(
                Hero("Shantala Devi", "Queen & Dancer", "12th Century", imageUrl = ""),
                Hero("Vishnuvardhana", "King", "1108 - 1152", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Vishnuvardhana.jpg/330px-Vishnuvardhana.jpg")
            ),
            "Bengaluru Rural" to listOf(
                Hero("Kempe Gowda II", "Administrator", "16th Century", imageUrl = ""),
                Hero("Dodda Kempe Gowda", "Administrator", "16th Century", imageUrl = "")
            ),
            "Bidar" to listOf(
                Hero("Mahmud Gawan", "Scholar & Statesman", "1411 - 1481", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Mahmud_Gawan.jpg/330px-Mahmud_Gawan.jpg"),
                Hero("Basavanna", "Social Reformer", "1131 - 1167", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Basavanna.jpg/330px-Basavanna.jpg")
            ),
            "Chamarajanagar" to listOf(
                Hero("Chamaraja Wadiyar", "King", "1863 - 1894", imageUrl = ""),
                Hero("Linga Gowda", "Freedom Fighter", "19th Century", imageUrl = "")
            ),
            "Chikkaballapura" to listOf(
                Hero("Sir M. Visvesvaraya", "Engineer & Statesman", "1861 - 1962", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Sir_MV.jpg/330px-Sir_MV.jpg"),
                Hero("Nalwadi Krishnaraja Wadiyar", "Administrator", "1884 - 1940", imageUrl = "")
            ),
            "Davanagere" to listOf(
                Hero("Shishunala Sharifa", "Saint & Poet", "1819 - 1888", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Shishunala_Sharifa.jpg/330px-Shishunala_Sharifa.jpg"),
                Hero("P. Lankesh", "Writer & Journalist", "1935 - 2000", imageUrl = "")
            ),
            "Gadag" to listOf(
                Hero("Basavanna", "Social Reformer", "1131 - 1167", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Basavanna.jpg/330px-Basavanna.jpg"),
                Hero("Akka Mahadevi", "Saint Poet", "12th Century", imageUrl = "")
            ),
            "Haveri" to listOf(
                Hero("Kadamba Mayurasharma", "King", "345 - 365 AD", imageUrl = ""),
                Hero("Shivarudrappa", "Poet & Writer", "1926 - 2013", imageUrl = "")
            ),
            "Kodagu" to listOf(
                Hero("General Cariappa", "First Indian Army Chief", "1899 - 1993", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/General_Cariappa.jpg/330px-General_Cariappa.jpg"),
                Hero("Mudduraja", "King", "Historical Era", imageUrl = "")
            ),
            "Kolar" to listOf(
                Hero("Siddalingaiah", "Poet & Writer", "1954 - 2021", imageUrl = ""),
                Hero("Bhaktavatsala", "Freedom Fighter", "19th Century", imageUrl = "")
            ),
            "Koppal" to listOf(
                Hero("Kanakadasa", "Saint & Poet", "1509 - 1609", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Kanakadasa.jpg/330px-Kanakadasa.jpg"),
                Hero("Kumaravyasa", "Poet", "15th Century", imageUrl = "")
            ),
            "Raichur" to listOf(
                Hero("Kanakadasa", "Saint & Poet", "1509 - 1609", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Kanakadasa.jpg/330px-Kanakadasa.jpg")
            ),
            "Ramanagara" to listOf(
                Hero("Kempe Gowda I", "Founder of Bengaluru", "1510 - 1569", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Kempegowda_I.jpg/330px-Kempegowda_I.jpg"),
                Hero("Thippaiah", "Freedom Fighter", "19th Century", imageUrl = "")
            ),
            "Tumakuru" to listOf(
                Hero("Siddalingaiah", "Poet & Writer", "1954 - 2021", imageUrl = ""),
                Hero("Thippaiah", "Freedom Fighter", "19th Century", imageUrl = "")
            ),
            "Udupi" to listOf(
                Hero("Madhvacharya", "Philosopher", "1238 - 1317", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Madhvacharya.jpg/330px-Madhvacharya.jpg"),
                Hero("Kanakadasa", "Saint & Poet", "1509 - 1609", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Kanakadasa.jpg/330px-Kanakadasa.jpg")
            ),
            "Uttara Kannada" to listOf(
                Hero("Karnad Sadashiva Rao", "Freedom Fighter", "1865 - 1919", imageUrl = ""),
                Hero("Shivarama Karanth", "Writer", "1902 - 1997", imageUrl = "")
            ),
            "Vijayapura" to listOf(
                Hero("Basavanna", "Social Reformer", "1131 - 1167", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Basavanna.jpg/330px-Basavanna.jpg"),
                Hero("Akka Mahadevi", "Saint Poet", "12th Century", imageUrl = "")
            ),
            "Mandya" to listOf(
                Hero("S. Nijalingappa", "Political Leader", "1902 - 2000", imageUrl = ""),
                Hero("Puttanna Chetty", "Social Reformer", "1862 - 1910", imageUrl = "")
            ),
            "Chikkamagaluru" to listOf(
                Hero("Kadamba Mayurasharma", "King", "345 - 365 AD", imageUrl = ""),
                Hero("Rukmangada", "King", "Historical Era", imageUrl = "")
            ),
            "Bagalkot" to listOf(
                Hero("Akka Mahadevi", "Saint Poet", "12th Century", imageUrl = ""),
                Hero("Basavanna", "Social Reformer", "1131 - 1167", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Basavanna.jpg/330px-Basavanna.jpg")
            ),
            "Yadgir" to listOf(
                Hero("Basavanna", "Social Reformer", "1131 - 1167", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Basavanna.jpg/330px-Basavanna.jpg"),
                Hero("Sharif Saheb", "Saint & Poet", "1819 - 1888", imageUrl = "")
            )
        )

        val match = allHeroes.entries.find {
            it.key.equals(district.trim(), ignoreCase = true)
        }
        return match?.value ?: listOf(
            Hero("$district Hero", "Freedom Fighter", "Historical Era"),
            Hero("Local Legend", "Social Reformer", "Historical Era")
        )
    }
}