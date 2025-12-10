package com.blivtech.syncshift.ui.addEmployee

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blivtech.syncshift.R
import com.blivtech.syncshift.data.model.request.EmployeeRequest

class EmployeeListAdapter : RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>() {

    private val items = mutableListOf<EmployeeRequest>()

    fun submitList(newList: List<EmployeeRequest>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvName)
        val designation: TextView = view.findViewById(R.id.tvAge)
        val city: TextView = view.findViewById(R.id.tvCity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_employee, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = items[position]
        holder.name.text = employee.employee_name
        holder.designation.text = employee.designation
        holder.city.text = employee.city
    }

    override fun getItemCount(): Int = items.size
}
