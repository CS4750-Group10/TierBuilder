package com.cpp.tierbuilder

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Add a callback interface
interface TierRowEditListener {
    fun onEditTitleClicked(position: Int)
}

class TierRowAdapter(private val editListener: TierRowEditListener) : RecyclerView.Adapter<TierRowAdapter.TierRowViewHolder>() {

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
        val editTitleEditText: EditText = itemView.findViewById(R.id.editTitleEditText)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TierRowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_tier_row, parent, false)
        return TierRowViewHolder(view)
    }

    override fun onBindViewHolder(holder: TierRowViewHolder, position: Int) {
        val tierRow = tierRows[position]
        holder.labelTextView.text = tierRow.title
        holder.labelTextView.setOnClickListener {
            editListener.onEditTitleClicked(position)
        }

        holder.editTitleEditText.inputType =
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES

        // Update the visibility of views based on the edit mode
        if (tierRow.isEditing) {
            holder.labelTextView.visibility = View.GONE
            holder.editTitleEditText.visibility = View.VISIBLE
            holder.editTitleEditText.setText(tierRow.title)

            // Set a listener to update the title when the user finishes editing
            holder.editTitleEditText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    tierRow.title = holder.editTitleEditText.text.toString()
                    tierRow.isEditing = false
                    notifyItemChanged(position)
                    // close the keyboard after clicking "Done"
                    val inputMethodManager =
                        holder.editTitleEditText.context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(holder.editTitleEditText.windowToken, 0)
                    true
                } else {
                    false
                }
            }
        } else {
            holder.labelTextView.visibility = View.VISIBLE
            holder.editTitleEditText.visibility = View.GONE
        }

        when (position) {
            0 -> holder.labelTextView.setBackgroundColor(Color.parseColor("#FF0000")) // Red
            1 -> holder.labelTextView.setBackgroundColor(Color.parseColor("#FFA500")) // Orange
            2 -> holder.labelTextView.setBackgroundColor(Color.parseColor("#FFFF00")) // Yellow
            3 -> holder.labelTextView.setBackgroundColor(Color.parseColor("#00FF00")) // Green
            4 -> holder.labelTextView.setBackgroundColor(Color.parseColor("#0DD8E6")) // Turquoise
            5 -> holder.labelTextView.setBackgroundColor(Color.parseColor("#9457EB")) // Purple
            6 -> holder.labelTextView.setBackgroundColor(Color.parseColor("#EE82EE")) // Violet
            else -> holder.labelTextView.setBackgroundColor(Color.BLACK) // Default color
        } // You can set the color as per your requirement

    }

    override fun getItemCount(): Int {
        return tierRows.size
    }


    fun addRow(tierRow: TierRow) {
        tierRows.add(tierRow)
        notifyItemInserted(tierRows.size - 1)
    }

    fun deleteRow(position: Int) {
        if (position in 0 until tierRows.size) {
            tierRows.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getTierRow(position: Int): TierRow {
        return tierRows[position]
    }

}
