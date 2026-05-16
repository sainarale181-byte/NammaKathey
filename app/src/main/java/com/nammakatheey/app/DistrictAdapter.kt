package com.nammakatheey.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class DistrictAdapter(
    private val districts: MutableList<String>,
    private val onDistrictClick: (String) -> Unit
) : RecyclerView.Adapter<DistrictAdapter.DistrictViewHolder>() {

    inner class DistrictViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDistrictName: TextView = itemView.findViewById(R.id.tvDistrictName)
        val tvHeroCount: TextView = itemView.findViewById(R.id.tvHeroCount)
        val tvFamousHero: TextView = itemView.findViewById(R.id.tvFamousHero)
        val tvDistrictEmoji: TextView = itemView.findViewById(R.id.tvDistrictEmoji)
        val cardView: MaterialCardView = itemView as MaterialCardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_district, parent, false)
        return DistrictViewHolder(view)
    }

    override fun onBindViewHolder(holder: DistrictViewHolder, position: Int) {
        val districtName = districts[position]
        holder.tvDistrictName.text = districtName
        holder.tvHeroCount.text = getHeroCount(districtName)
        holder.tvFamousHero.text = getFamousHero(districtName)
        holder.tvDistrictEmoji.text = getDistrictEmoji(districtName)

        holder.cardView.setOnClickListener {
            onDistrictClick(districtName)
        }
    }

    override fun getItemCount(): Int = districts.size

    private fun getHeroCount(district: String): String {
        val counts = mapOf(
            "Mysuru" to "4 Heroes",
            "Belagavi" to "3 Heroes",
            "Dakshina Kannada" to "2 Heroes",
            "Shivamogga" to "2 Heroes",
            "Chitradurga" to "2 Heroes",
            "Bengaluru Urban" to "3 Heroes",
            "Hassan" to "2 Heroes",
            "Dharwad" to "2 Heroes",
            "Kalaburagi" to "2 Heroes",
            "Ballari" to "2 Heroes",
            "Udupi" to "2 Heroes",
            "Vijayapura" to "2 Heroes",
            "Koppal" to "2 Heroes",
            "Gadag" to "2 Heroes",
            "Bagalkot" to "2 Heroes",
            "Bidar" to "2 Heroes",
            "Tumakuru" to "2 Heroes",
            "Mandya" to "2 Heroes",
            "Ramanagara" to "2 Heroes",
            "Uttara Kannada" to "2 Heroes",
            "Chikkaballapura" to "2 Heroes",
            "Chikkamagaluru" to "2 Heroes",
            "Davanagere" to "2 Heroes",
            "Haveri" to "2 Heroes",
            "Kodagu" to "2 Heroes",
            "Kolar" to "2 Heroes",
            "Chamarajanagar" to "2 Heroes",
            "Bengaluru Rural" to "2 Heroes",
            "Yadgir" to "2 Heroes"
        )
        return counts[district] ?: "1 Hero"
    }

    private fun getFamousHero(district: String): String {
        val heroes = mapOf(
            "Mysuru" to "Home of Tipu Sultan",
            "Belagavi" to "Home of Kittur Chennamma",
            "Shivamogga" to "Home of Kuvempu",
            "Chitradurga" to "Home of Onake Obavva",
            "Dakshina Kannada" to "Home of Shivaram Karanth",
            "Dharwad" to "Home of Basavanna",
            "Bengaluru Urban" to "Home of Kempe Gowda",
            "Kalaburagi" to "Home of Basavanna",
            "Ballari" to "Home of Onake Obavva",
            "Hassan" to "Home of Vishnuvardhana",
            "Udupi" to "Home of Madhvacharya",
            "Vijayapura" to "Home of Akka Mahadevi",
            "Koppal" to "Home of Kanakadasa",
            "Raichur" to "Home of Kanakadasa",
            "Bidar" to "Home of Mahmud Gawan",
            "Kodagu" to "Home of General Cariappa",
            "Chikkaballapura" to "Home of Sir M. Visvesvaraya",
            "Davanagere" to "Home of Shishunala Sharifa",
            "Mandya" to "Home of S. Nijalingappa",
            "Tumakuru" to "Home of Siddalingaiah",
            "Gadag" to "Home of Akka Mahadevi",
            "Bagalkot" to "Home of Basavanna",
            "Yadgir" to "Home of Basavanna",
            "Haveri" to "Home of Kadamba Mayurasharma",
            "Uttara Kannada" to "Home of Shivarama Karanth",
            "Ramanagara" to "Home of Kempe Gowda I",
            "Bengaluru Rural" to "Home of Kempe Gowda II",
            "Chikkamagaluru" to "Home of Kadamba Mayurasharma",
            "Chamarajanagar" to "Home of Chamaraja Wadiyar",
            "Kolar" to "Home of Siddalingaiah"
        )
        return heroes[district] ?: "Explore local heroes"
    }

    private fun getDistrictEmoji(district: String): String {
        val emojis = mapOf(
            "Mysuru" to "👑",
            "Belagavi" to "⚔️",
            "Shivamogga" to "📖",
            "Chitradurga" to "🏯",
            "Dakshina Kannada" to "🌊",
            "Dharwad" to "🎵",
            "Bengaluru Urban" to "🏙️",
            "Kalaburagi" to "🕌",
            "Ballari" to "🗿",
            "Hassan" to "🛕",
            "Udupi" to "🙏",
            "Vijayapura" to "🏛️",
            "Koppal" to "🌟",
            "Raichur" to "🎶",
            "Bidar" to "📚",
            "Kodagu" to "🎖️",
            "Chikkaballapura" to "⚙️",
            "Davanagere" to "🎤",
            "Mandya" to "🌾",
            "Tumakuru" to "✊",
            "Gadag" to "🕊️",
            "Bagalkot" to "🏺",
            "Yadgir" to "🌿",
            "Haveri" to "🏰",
            "Uttara Kannada" to "🌲",
            "Ramanagara" to "🦁",
            "Bengaluru Rural" to "🌄",
            "Chikkamagaluru" to "☕",
            "Chamarajanagar" to "🐘",
            "Kolar" to "✍️"
        )
        return emojis[district] ?: "🏛"
    }
}