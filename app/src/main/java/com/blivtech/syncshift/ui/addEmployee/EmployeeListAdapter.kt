package com.blivtech.syncshift.ui.addEmployee

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blivtech.syncshift.databinding.ItemEmployeeBinding
import com.blivtech.syncshift.data.model.request.EmployeeRequest

class EmployeeListAdapter :
    ListAdapter<EmployeeRequest, EmployeeListAdapter.EmployeeViewHolder>(DiffCallback) {

    inner class EmployeeViewHolder(val binding: ItemEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EmployeeRequest) = binding.apply {

            // Name + ID
            tvName.text = "${item.employee_name} (${item.employee_id})"

            // City
            tvCity.text = item.city

            // WhatsApp-style Initials
            val initials = getInitials(item.employee_name)
            tvInitials.text = initials
            binding.bgCircle.background.setTint(getWhatsAppColor())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = ItemEmployeeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<EmployeeRequest>() {
        override fun areItemsTheSame(
            oldItem: EmployeeRequest,
            newItem: EmployeeRequest
        ) = oldItem.employee_id == newItem.employee_id

        override fun areContentsTheSame(
            oldItem: EmployeeRequest,
            newItem: EmployeeRequest
        ) = oldItem == newItem
    }
    fun getInitials(name: String): String {
        val parts = name.trim().split(" ")

        return when (parts.size) {
            0 -> ""
            1 -> parts[0].take(1).uppercase()
            else -> (parts[0].take(1) + parts.last().take(1)).uppercase()
        }
    }

    fun getWhatsAppColor(): Int {
        val colors = listOf(
            Color.parseColor("#1EBEA5"),
            Color.parseColor("#25D366"),
            Color.parseColor("#128C7E"),
            Color.parseColor("#34B7F1"),
            Color.parseColor("#075E54")
        )
        return colors.random()
    }

}
