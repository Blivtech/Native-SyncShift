package com.blivtech.syncshift.ui.company

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blivtech.syncshift.databinding.ChildItemShilftSelectionBinding

class ShiftSelectionAdapter() : ListAdapter<ShiftItem, ShiftSelectionAdapter.CompanyViewHolder>(DiffCallback()) {

    inner class CompanyViewHolder(private val binding: ChildItemShilftSelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShiftItem) {

            binding.txtShiftName.text = item.name
            binding.txtShiftTime.text = item.time
            binding.checkBox.isChecked = item.isSelected

            binding.checkBox.setOnCheckedChangeListener(null)

            binding.checkBox.isChecked = item.isSelected

            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->

                val updatedList = currentList.toMutableList()
                updatedList[adapterPosition] =
                    updatedList[adapterPosition].copy(isSelected = isChecked)

                submitList(updatedList)

            }

            binding.root.setOnClickListener {
                binding.checkBox.performClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {

        val binding = ChildItemShilftSelectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CompanyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<ShiftItem>() {

        override fun areItemsTheSame(
            oldItem: ShiftItem,
            newItem: ShiftItem
        ): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(
            oldItem: ShiftItem,
            newItem: ShiftItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}