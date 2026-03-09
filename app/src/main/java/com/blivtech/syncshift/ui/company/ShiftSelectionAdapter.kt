package com.blivtech.syncshift.ui.company

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blivtech.syncshift.databinding.ChildItemShilftSelectionBinding

class ShiftSelectionAdapter :
    ListAdapter<ShiftEntity, ShiftSelectionAdapter.ShiftViewHolder>(DiffCallback()) {

    private val selectedShiftCodes = mutableSetOf<String>()

    inner class ShiftViewHolder(
        private val binding: ChildItemShilftSelectionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShiftEntity) {

            binding.txtShiftName.text = item.shiftName
            binding.txtShiftTime.text = "${item.startTime.substring(0,5)} - ${item.endTime.substring(0,5)}"

            binding.checkBox.setOnCheckedChangeListener(null)

            binding.checkBox.isChecked = selectedShiftCodes.contains(item.shiftCode)

            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->

                if (isChecked) {
                    selectedShiftCodes.add(item.shiftCode)
                } else {
                    selectedShiftCodes.remove(item.shiftCode)
                }
            }

            binding.root.setOnClickListener {
                binding.checkBox.performClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShiftViewHolder {

        val binding = ChildItemShilftSelectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ShiftViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShiftViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    fun getSelectedItems(): List<ShiftEntity> {
        return currentList.filter { selectedShiftCodes.contains(it.shiftCode) }
    }

    fun setSelectedShifts(shiftCodes: List<String>) {
        selectedShiftCodes.clear()
        selectedShiftCodes.addAll(shiftCodes)
        notifyDataSetChanged()
    }

    class DiffCallback : DiffUtil.ItemCallback<ShiftEntity>() {

        override fun areItemsTheSame(
            oldItem: ShiftEntity,
            newItem: ShiftEntity
        ): Boolean {
            return oldItem.shiftCode == newItem.shiftCode
        }

        override fun areContentsTheSame(
            oldItem: ShiftEntity,
            newItem: ShiftEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}