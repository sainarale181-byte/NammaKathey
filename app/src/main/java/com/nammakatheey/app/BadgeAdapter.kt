package com.nammakatheey.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BadgeAdapter(
    private val badges: List<String>
) : RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder>() {

    inner class BadgeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBadgeName: TextView = itemView.findViewById(R.id.tvBadgeName)
        val tvBadgeEmoji: TextView = itemView.findViewById(R.id.tvBadgeEmoji)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_badge, parent, false)
        return BadgeViewHolder(view)
    }

    override fun onBindViewHolder(holder: BadgeViewHolder, position: Int) {
        val badge = badges[position]
        holder.tvBadgeName.text = badge
        holder.tvBadgeEmoji.text = "🏅"
    }

    override fun getItemCount(): Int = badges.size
}