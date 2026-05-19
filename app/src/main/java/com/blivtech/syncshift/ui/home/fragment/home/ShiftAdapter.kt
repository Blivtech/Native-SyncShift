package com.blivtech.syncshift.ui.home.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blivtech.syncshift.databinding.ItemShiftHomeBinding
import com.blivtech.syncshift.ui.company.ShiftItem

class ShiftAdapter (
    private val onClick: (ShiftItem) -> Unit
):
    ListAdapter<ShiftItem, ShiftAdapter.ShiftViewHolder>(ShiftDiffUtil()) {

    inner class ShiftViewHolder(
        private val binding: ItemShiftHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShiftItem) {

            val shiftTime = "${item.startTime} - ${item.endTime}"

            /** NOT COMPLETED CARD **/
            binding.tvShiftName.text = item.shiftName
            binding.tvShiftTime.text = shiftTime

            /** COMPLETED CARD **/
            binding.tvShiftNameCD.text = item.shiftName
            binding.tvShiftTimeCD.text = shiftTime

            binding.tvPresentCount.text = item.present.toString()
            binding.tvAbsentCount.text = item.absent.toString()
            if (item.status == "0") {

                binding.cvNotCompleted.visibility = View.VISIBLE
                binding.cvCompleted.visibility = View.GONE

            } else {

                binding.cvNotCompleted.visibility = View.GONE
                binding.cvCompleted.visibility = View.VISIBLE
            }
            binding.btnDoAttendance.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShiftViewHolder {

        val binding = ItemShiftHomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ShiftViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ShiftViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}


/** DiffUtil **/

class ShiftDiffUtil : DiffUtil.ItemCallback<ShiftItem>() {

    override fun areItemsTheSame(
        oldItem: ShiftItem,
        newItem: ShiftItem
    ): Boolean {
        return oldItem.shiftCode == newItem.shiftCode
    }

    override fun areContentsTheSame(
        oldItem: ShiftItem,
        newItem: ShiftItem
    ): Boolean {
        return oldItem == newItem
    }
}