package com.blivtech.syncshift.ui.home.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blivtech.syncshift.data.model.GridItem
import com.blivtech.syncshift.databinding.ChildItemMenuBinding

class MenuAdapter(
    private val onItemClick: (GridItem) -> Unit
) : ListAdapter<GridItem, MenuAdapter.GridViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GridItem>() {
            override fun areItemsTheSame(oldItem: GridItem, newItem: GridItem): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: GridItem, newItem: GridItem): Boolean =
                oldItem == newItem
        }
    }

    inner class GridViewHolder(private val binding: ChildItemMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GridItem) {
            binding.m1Icon.setImageResource(item.iconResId)
            binding.tvLabel.text = item.title

            // Click with animation
            binding.root.setOnClickListener {
                binding.root.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100)
                    .withEndAction {
                        binding.root.animate().scaleX(1f).scaleY(1f).duration = 100
                        onItemClick(item)
                    }.start()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val binding = ChildItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
