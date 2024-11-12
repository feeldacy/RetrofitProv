package com.example.retrofitprov

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class ProvAdapter(private val prov: List<String>) :
    RecyclerView.Adapter<ProvAdapter.ProvViewHolder>() {

    class ProvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvProv: TextView = itemView.findViewById(R.id.tv_prov)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_prov, parent, false)
        return ProvViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProvViewHolder, position: Int) {
        holder.tvProv.text = prov[position]
    }

    override fun getItemCount(): Int = prov.size


}