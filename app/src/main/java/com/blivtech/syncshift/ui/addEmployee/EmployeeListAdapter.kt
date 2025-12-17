package com.blivtech.syncshift.ui.addEmployee

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blivtech.syncshift.data.model.local.EmployeeEntity
import com.blivtech.syncshift.databinding.ItemEmployeeBinding
import com.blivtech.syncshift.data.model.request.EmployeeRequest

class EmployeeListAdapter :
    ListAdapter<EmployeeEntity, EmployeeListAdapter.EmployeeViewHolder>(DiffCallback) {

    inner class EmployeeViewHolder(val binding: ItemEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EmployeeEntity) = binding.apply {

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

    object DiffCallback : DiffUtil.ItemCallback<EmployeeEntity>() {
        override fun areItemsTheSame(
            oldItem: EmployeeEntity,
            newItem: EmployeeEntity
        ) = oldItem.employee_id == newItem.employee_id

        override fun areContentsTheSame(
            oldItem: EmployeeEntity,
            newItem: EmployeeEntity
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
            Color.parseColor("#1EBEA5"), // WhatsApp green
            Color.parseColor("#25D366"), // light green
            Color.parseColor("#128C7E"), // dark teal
            Color.parseColor("#075E54"), // WhatsApp dark green
            Color.parseColor("#34B7F1"), // light blue

            Color.parseColor("#2ECC71"), // emerald
            Color.parseColor("#27AE60"), // green
            Color.parseColor("#16A085"), // teal
            Color.parseColor("#00BFA5"), // aqua green
            Color.parseColor("#00ACC1"), // cyan

            Color.parseColor("#039BE5"), // blue
            Color.parseColor("#0288D1"), // deep blue
            Color.parseColor("#26C6DA"), // sky blue
            Color.parseColor("#4DD0E1"), // light cyan
            Color.parseColor("#80DEEA"), // pastel cyan

            Color.parseColor("#A5D6A7"), // soft green
            Color.parseColor("#81C784"), // pastel green
            Color.parseColor("#66BB6A"), // medium green
            Color.parseColor("#4CAF50"), // material green
            Color.parseColor("#009688")  // material teal
        )

        return colors.random()
    }

}
