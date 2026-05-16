package com.nammakatheey.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.nammakatheey.app.data.model.Hero

class HeroAdapter(
    private val heroes: List<Hero>,
    private val onHeroClick: (Hero) -> Unit
) : RecyclerView.Adapter<HeroAdapter.HeroViewHolder>() {

    inner class HeroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHeroName: TextView = itemView.findViewById(R.id.tvHeroName)
        val tvHeroType: TextView = itemView.findViewById(R.id.tvHeroType)
        val tvHeroEra: TextView = itemView.findViewById(R.id.tvHeroEra)
        val btnReadStory: MaterialButton = itemView.findViewById(R.id.btnReadStory)
        val ivHeroImage: ImageView = itemView.findViewById(R.id.ivHeroImage)
        val cardView: MaterialCardView = itemView as MaterialCardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hero, parent, false)
        return HeroViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val hero = heroes[position]
        holder.tvHeroName.text = hero.name
        holder.tvHeroType.text = hero.type
        holder.tvHeroEra.text = "Era: ${hero.era}"

        // Load hero image using Glide
        if (hero.imageUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(hero.imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(holder.ivHeroImage)
        } else {
            holder.ivHeroImage.setImageResource(R.drawable.ic_launcher_foreground)
        }

        // Read Story button click
        holder.btnReadStory.setOnClickListener {
            onHeroClick(hero)
        }

        // Card click also works
        holder.cardView.setOnClickListener {
            onHeroClick(hero)
        }
    }

    override fun getItemCount(): Int = heroes.size
}