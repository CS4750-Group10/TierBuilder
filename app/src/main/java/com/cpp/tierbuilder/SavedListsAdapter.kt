package com.cpp.tierbuilder

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cpp.tierbuilder.databinding.ListItemTierlistBinding
import java.util.UUID

class ListHolder(
    val binding: ListItemTierlistBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        tierList: TierList,
        onListClicked: (listId: UUID) -> Unit,
        onListLongClicked: (listId: UUID) -> Boolean
    ) {
        binding.tierlistTitle.text = tierList.title

        binding.root.setOnClickListener {
            onListClicked(tierList.id)
        }

        binding.root.setOnLongClickListener {
            onListLongClicked(tierList.id)
        }

        if (tierList.isSelected) {
            binding.root.setBackgroundColor(Color.LTGRAY)
            binding.tierlistSelected.visibility = View.VISIBLE
        } else {
            binding.root.setBackgroundColor(Color.TRANSPARENT)
            binding.tierlistSelected.visibility = View.GONE
        }
    }
}

class SavedListsAdapter(
    private val tierLists: List<TierList>,
    private val onListClicked: (listId: UUID) -> Unit,
    private val onListLongClicked: (listId: UUID) -> Boolean
) : RecyclerView.Adapter<ListHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTierlistBinding.inflate(inflater, parent, false)
        return ListHolder(binding)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val tierlist = tierLists[position]
        holder.bind(tierlist, onListClicked, onListLongClicked)
    }

    override fun getItemCount(): Int = tierLists.size
}