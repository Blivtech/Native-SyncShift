package com.blivtech.syncshift.ui.bottomsheet

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.blivtech.syncshift.R
import com.blivtech.syncshift.data.model.ShiftTiming
import com.blivtech.syncshift.databinding.ItemShiftTimingBinding

class ShiftTimingAdapter(
    private val list: MutableList<ShiftTiming>,
    private val onSelect: (ShiftTiming) -> Unit
) : RecyclerView.Adapter<ShiftTimingAdapter.VH>() {

    inner class VH(val binding: ItemShiftTimingBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemShiftTimingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = list[position]

        holder.binding.tvShiftName.text = item.name
        holder.binding.tvShiftTime.text = item.time
        holder.binding.radio.isChecked = item.isSelected

        holder.binding.root.strokeWidth = if (item.isSelected) 2 else 1
        holder.binding.root.strokeColor =
            if (item.isSelected)
                ContextCompat.getColor(holder.itemView.context, R.color.button_color)
            else Color.parseColor("#E0E0E0")

        holder.binding.root.setOnClickListener {
            list.forEach { it.isSelected = false }
            item.isSelected = true
            notifyDataSetChanged()
            onSelect(item)
        }
    }

    override fun getItemCount() = list.size
}
