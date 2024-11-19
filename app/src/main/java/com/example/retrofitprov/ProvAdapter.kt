package com.example.retrofitprov

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class ProvAdapter(
    private val prov: List<String>,
    private val favoriteProvinces: Set<String>,
    private val onBookmarkClicked: (String, Boolean) -> Unit
    ): RecyclerView.Adapter<ProvAdapter.ProvViewHolder>() {

    private val bookmarkedItems = favoriteProvinces.toMutableSet()

    class ProvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvProv: TextView = itemView.findViewById(R.id.tv_prov)
        val bookmarkIcon: ImageView = itemView.findViewById(R.id.icon_bookmark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_prov, parent, false)
        return ProvViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProvViewHolder, position: Int) {
        val province = prov[position]
        holder.tvProv.text = province

        val isBookmarked = bookmarkedItems.contains(province)
        holder.bookmarkIcon.setImageResource(
            if (isBookmarked) {
                R.drawable.baseline_bookmark_24
            } else {
                R.drawable.baseline_bookmark_border_24
            }
        )

        holder.bookmarkIcon.setOnClickListener {
            val newBookmarkState = !isBookmarked
            onBookmarkClicked(province, newBookmarkState)
        }
    }

    override fun getItemCount(): Int = prov.size

}