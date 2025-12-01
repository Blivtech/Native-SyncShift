package com.blivtech.syncshift.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blivtech.syncshift.data.model.Employee
import com.blivtech.syncshift.data.repository.EmployeeRepository

class EmployeeViewModel: ViewModel() {

    private val repo = EmployeeRepository()

    private var masterList = mutableListOf<Employee>()

    val employeeListLiveData = MutableLiveData<List<Employee>>()

    var currentFilter = "All"

    fun loadEmployees() {
        masterList = repo.loadDummyEmployees()
        employeeListLiveData.value = masterList
    }

    fun updateStatus(emp: Employee, status: String) {

        masterList.find { it.id == emp.id }?.status = status

        applyFilter()
    }

    fun applyFilter() {
        val filtered = when (currentFilter) {
            "Present" -> masterList.filter { it.status == "Present" }
            "Absent"  -> masterList.filter { it.status == "Absent" }
            else      -> masterList
        }

        employeeListLiveData.value = filtered
    }

    fun search(query: String) {

        val q = query.lowercase()

        val filtered = masterList.filter { it.name.lowercase().contains(q) }

        employeeListLiveData.value = filtered
    }

    fun getPresentCount(): Int {
        return masterList.count { it.status == "Present" }
    }

    fun getAbsentCount(): Int {
        return masterList.count { it.status == "Absent" }
    }

}