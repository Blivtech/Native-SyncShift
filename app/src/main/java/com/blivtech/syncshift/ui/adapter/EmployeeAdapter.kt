package com.blivtech.syncshift.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blivtech.syncshift.R
import com.blivtech.syncshift.data.model.Employee
import com.blivtech.syncshift.databinding.ItemEmployeeBinding

class EmployeeAdapter(
    private var list: MutableList<Employee>,
    private val onStatusChanged: (Employee, String) -> Unit
) : RecyclerView.Adapter<EmployeeAdapter.VH>() {

    inner class VH(val binding: ItemEmployeeBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(emp: Employee) {

            binding.tvName.text = emp.name
            binding.tvAge.text = "Age: ${emp.age}"
            binding.tvCity.text = "City: ${emp.city}"

            if (emp.gender == "Male")
                binding.imgProfile.setImageResource(R.drawable.ic_male_round)
            else
                binding.imgProfile.setImageResource(R.drawable.ic_female_round)

            updateButtons(emp)

            binding.btnPresent.setOnClickListener {
                onStatusChanged(emp, "Present")
                emp.status = "Present"
                updateButtons(emp)
            }

            binding.btnAbsent.setOnClickListener {
                onStatusChanged(emp, "Absent")
                emp.status = "Absent"
                updateButtons(emp)
            }
        }

        private fun updateButtons(emp: Employee) {

            when (emp.status) {

                "Present" -> {
                    binding.btnPresent.setBackgroundResource(R.drawable.filter_present)
                    binding.btnPresent.setTextColor(Color.WHITE)

                    binding.btnAbsent.setBackgroundResource(R.drawable.filter_unselected)
                    binding.btnAbsent.setTextColor(Color.BLACK)
                }

                "Absent" -> {
                    binding.btnAbsent.setBackgroundResource(R.drawable.filter_absent)
                    binding.btnAbsent.setTextColor(Color.WHITE)

                    binding.btnPresent.setBackgroundResource(R.drawable.filter_unselected)
                    binding.btnPresent.setTextColor(Color.BLACK)
                }

                else -> {
                    binding.btnPresent.setBackgroundResource(R.drawable.filter_unselected)
                    binding.btnAbsent.setBackgroundResource(R.drawable.filter_unselected)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemEmployeeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position])
    }

    fun updateList(newList: List<Employee>) {
        list = newList.toMutableList()
        notifyDataSetChanged()
    }

}