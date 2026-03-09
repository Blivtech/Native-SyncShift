package com.blivtech.syncshift.ui.addEmployee

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blivtech.syncshift.data.model.response.AvatarColor
import com.blivtech.syncshift.data.model.local.Entity.EmployeeEntity
import com.blivtech.syncshift.databinding.ChildItemEmployeeBinding
import com.blivtech.syncshift.utils.TimeUtils

class EmployeeListAdapter :
    ListAdapter<EmployeeEntity, EmployeeListAdapter.EmployeeViewHolder>(DiffCallback) {

    inner class EmployeeViewHolder(val binding: ChildItemEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EmployeeEntity) = binding.apply {

            txtName.text = "${item.employee_name} (${item.employee_id})"
            txtRole.text = item.designation
            txtPhonenumber.text = item.phone
            txtJoined.text = TimeUtils.getConvertedDate(
                TimeUtils.FORMAT_5,
                TimeUtils.FORMAT_9,
                item.joining_date
            )

            val initials = getInitials(item.employee_name)
            tvInitials.text = initials

            val avatarColor = getWhatsAppColor(item.employee_name)

            bgCircle.background.setTint(avatarColor.bgColor)
            tvInitials.setTextColor(avatarColor.textColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = ChildItemEmployeeBinding.inflate(
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

    fun getWhatsAppColor(name: String): AvatarColor {

        val colors = listOf(
            AvatarColor(
                bgColor = Color.parseColor("#FCE4EC"), // light pink
                textColor = Color.parseColor("#AD1457") // dark pink
            ),
            AvatarColor(
                bgColor = Color.parseColor("#E8F5E9"), // light green
                textColor = Color.parseColor("#2E7D32") // dark green
            ),
            AvatarColor(
                bgColor = Color.parseColor("#E0F2F1"), // light teal
                textColor = Color.parseColor("#00695C") // dark teal
            ),
            AvatarColor(
                bgColor = Color.parseColor("#E3F2FD"), // light blue
                textColor = Color.parseColor("#1565C0") // dark blue
            ),
            AvatarColor(
                bgColor = Color.parseColor("#F3E5F5"), // light purple
                textColor = Color.parseColor("#6A1B9A") // dark purple
            ),
            AvatarColor(
                bgColor = Color.parseColor("#FFFDE7"), // light yellow
                textColor = Color.parseColor("#F9A825") // dark yellow
            ),
            AvatarColor(
                bgColor = Color.parseColor("#ECEFF1"), // light grey
                textColor = Color.parseColor("#37474F") // dark grey
            )
        )

        val index = kotlin.math.abs(name.hashCode()) % colors.size
        return colors[index]
    }



}
