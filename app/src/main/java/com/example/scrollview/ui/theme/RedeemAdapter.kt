package com.example.scrollview.ui.theme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scrollview.R

class RedeemAdapter(private val items: List<RedeemItem>) :
    RecyclerView.Adapter<RedeemAdapter.RedeemViewHolder>() {

    class RedeemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCoins: TextView = view.findViewById(R.id.tvCoins)
        val tvAmount: TextView = view.findViewById(R.id.tvAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedeemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reedem_item, parent, false)
        return RedeemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RedeemViewHolder, position: Int) {
        val item = items[position]
        holder.tvCoins.text = item.coins
        holder.tvAmount.text = item.amount
    }

    override fun getItemCount(): Int = items.size
}
