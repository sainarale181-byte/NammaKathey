package com.nammakatheey.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.nammakatheey.app.data.model.StoryPage

class StoryPagerAdapter(
    private val pages: List<StoryPage>
) : RecyclerView.Adapter<StoryPagerAdapter.StoryPageViewHolder>() {

    inner class StoryPageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvStoryPageTitle)
        val tvText: TextView = itemView.findViewById(R.id.tvStoryText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryPageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_story_page, parent, false)
        return StoryPageViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryPageViewHolder, position: Int) {
        val page = pages[position]
        holder.tvTitle.text = page.title
        holder.tvText.text = page.text
    }

    override fun getItemCount(): Int = pages.size
}