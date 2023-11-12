package com.cpp.tierbuilder

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class TierRowAdapter : RecyclerView.Adapter<TierRowAdapter.TierRowViewHolder>() {

    private val tierRows = mutableListOf<TierRow>()

    init {
        generateDefaultRows()
    }
    private fun generateDefaultRows() {
        val defaultLabels = listOf("S", "A", "B", "C", "D", "E", "F")
        for ((index, label) in defaultLabels.withIndex()) {
            val tierRow = TierRow(label, index + 1, "Color", listOf())
            tierRows.add(tierRow)
        }
    }

    inner class TierRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // You can access views within your TierRowFragment layout here
        val labelTextView: TextView = itemView.findViewById(R.id.tierTextView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TierRowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_tier_row, parent, false)
        return TierRowViewHolder(view)
    }

    override fun onBindViewHolder(holder: TierRowViewHolder, position: Int) {
        val tierRow = tierRows[position]
        val label = tierRow.title
        holder.labelTextView.text = tierRow.title
        if (position == 0)
            holder.labelTextView.setTextColor(Color.RED)
        else if (position == 1)
            holder.labelTextView.setTextColor(Color.rgb(255, 165, 0))
        else if (position == 2)
            holder.labelTextView.setTextColor(Color.YELLOW)
        else if (position == 3)
            holder.labelTextView.setTextColor(Color.GREEN)
        else if (position == 4)
            holder.labelTextView.setTextColor(Color.rgb(13, 216, 230))
        else if (position == 5)
            holder.labelTextView.setTextColor(Color.rgb(148, 87, 235))
        else if (position == 6)
            holder.labelTextView.setTextColor(Color.rgb(238, 130, 238))
        else
            holder.labelTextView.setTextColor(Color.BLACK) // You can set the color as per your requirement

    }

    override fun getItemCount(): Int {
        return tierRows.size
    }


    fun addRow(tierRow: TierRow) {
        tierRows.add(tierRow)
        notifyDataSetChanged()
    }

    fun deleteRow(position: Int) {
        if (position in 0 until tierRows.size) {
            tierRows.removeAt(position)
            notifyDataSetChanged()
        }
    }
}
