package com.blivtech.syncshift.ui.attendance

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blivtech.syncshift.data.enumi.AttendanceStatus
import com.blivtech.syncshift.data.model.response.AvatarColor
import com.blivtech.syncshift.data.model.response.data.EmployeeAttendanceUI
import com.blivtech.syncshift.databinding.ChildItemAttendanceBinding


class EmployeeAttendanceAdapter(
    private val onStatusClick: (String, AttendanceStatus) -> Unit
) : ListAdapter<EmployeeAttendanceUI, EmployeeAttendanceAdapter.VH>(Diff()) {

    private var fullList: List<EmployeeAttendanceUI> = emptyList()
    private var currentTab: AttendanceStatus = AttendanceStatus.NOT_IN_YET

    override fun submitList(list: List<EmployeeAttendanceUI>?) {
        fullList = list ?: emptyList()
        filterByTab(currentTab)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ChildItemAttendanceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VH(
        private val binding: ChildItemAttendanceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EmployeeAttendanceUI) = with(binding) {

            tvName.text = item.name
            tvRole.text = item.designation
            tvPhone.text = item.phone

            resetButtons()

            when (currentTab) {
                AttendanceStatus.NOT_IN_YET -> {
                    btnNotIn.visibility = View.GONE
                }
                AttendanceStatus.PRESENT -> {
                    btnPresent.visibility = View.GONE
                }
                AttendanceStatus.ABSENT -> {
                    btnAbsent.visibility = View.GONE
                }
            }

            btnPresent.setOnClickListener {
                onStatusClick(item.employeeId, AttendanceStatus.PRESENT)
            }

            btnAbsent.setOnClickListener {
                onStatusClick(item.employeeId, AttendanceStatus.ABSENT)
            }

            btnNotIn.setOnClickListener {
                onStatusClick(item.employeeId, AttendanceStatus.NOT_IN_YET)
            }
            val initials = getInitials(item.name)
            tvInitials.text = initials

            val avatarColor = getWhatsAppColor(item.name)

            bgCircle.background.setTint(avatarColor.bgColor)
            tvInitials.setTextColor(avatarColor.textColor)

        }
    }

    // 🔥 TAB FILTER + BUTTON VISIBILITY CONTROLLER
    fun filterByTab(tabStatus: AttendanceStatus) {
        currentTab = tabStatus

        val filtered = fullList.filter { it.status == tabStatus }
        super.submitList(filtered)
    }

    class Diff : DiffUtil.ItemCallback<EmployeeAttendanceUI>() {
        override fun areItemsTheSame(o: EmployeeAttendanceUI, n: EmployeeAttendanceUI) =
            o.employeeId == n.employeeId

        override fun areContentsTheSame(o: EmployeeAttendanceUI, n: EmployeeAttendanceUI) =
            o == n
    }

    private fun ChildItemAttendanceBinding.resetButtons() {
        btnPresent.visibility = View.VISIBLE
        btnAbsent.visibility = View.VISIBLE
        btnNotIn.visibility = View.VISIBLE
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


