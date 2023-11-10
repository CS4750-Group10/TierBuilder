package com.cpp.tierbuilder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TierRowAdapter : RecyclerView.Adapter<TierRowAdapter.TierRowViewHolder>() {

    private val labels = listOf("S", "A", "B", "C", "D", "E", "F")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TierRowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_tier_row, parent, false)
        return TierRowViewHolder(view)
    }

    override fun onBindViewHolder(holder: TierRowViewHolder, position: Int) {
        val label = labels[position]
        holder.labelTextView.text = label
    }

    override fun getItemCount(): Int {
        // Return the number of rows you want to display (in this case, 7).
        return labels.size
    }

    class TierRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelTextView: TextView = itemView.findViewById(R.id.tierTextView)
    }
}
