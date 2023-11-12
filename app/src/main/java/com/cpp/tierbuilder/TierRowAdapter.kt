package com.cpp.tierbuilder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
<<<<<<< Updated upstream
=======
class TierRowAdapter : RecyclerView.Adapter<TierRowAdapter.TierRowViewHolder>() {

    private val tierRows = mutableListOf<TierRow>()
>>>>>>> Stashed changes

class TierRowAdapter : RecyclerView.Adapter<TierRowAdapter.TierRowViewHolder>() {

    private val labels = listOf("S", "A", "B", "C", "D", "E", "F")

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
        val label = labels[position]
        holder.labelTextView.text = label
    }

    override fun getItemCount(): Int {
        // Return the number of rows you want to display (in this case, 7).
        return labels.size
    }

<<<<<<< Updated upstream
    class TierRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelTextView: TextView = itemView.findViewById(R.id.tierTextView)
=======

    fun addRow(tierRow: TierRow) {
        tierRows.add(tierRow)
        notifyDataSetChanged()
    }

    fun deleteRow(position: Int) {
        if (position in 0 until tierRows.size) {
            tierRows.removeAt(position)
            notifyDataSetChanged()
        }
>>>>>>> Stashed changes
    }
}
