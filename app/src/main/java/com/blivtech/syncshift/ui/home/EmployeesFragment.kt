package com.blivtech.syncshift.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blivtech.syncshift.R
import com.blivtech.syncshift.data.model.Resource
import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.ui.addEmployee.EmployeeListAdapter
import com.blivtech.syncshift.ui.addEmployee.EmployeeViewModel
import com.blivtech.syncshift.ui.components.ProgressDialog
import com.blivtech.syncshift.utils.SharedPreferencesManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeesFragment : Fragment() {


    private val viewModel: EmployeeViewModel by viewModels()
    private lateinit var adapter: EmployeeListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reports, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = EmployeeListAdapter()
        recyclerView.adapter = adapter

        val employee = EmployeeRequest(
            employee_id = "EMP001",
            bt_code = SharedPreferencesManager.getLoginData(requireContext()).bt_code,
            employee_name = "Saravanan",
            city = "Chennai",
            salary_type = "Monthly",
            salary_code = "1",
            email = "john@example.com",
            phone = "9876543210",
            department = "IT",
            designation = "Developer",
            date_of_birth = "1995-05-10",
            joining_date = "2024-11-01",
            address = "Chennai",
            pincode = "600001",
            status = "Active"
        )
        viewModel.fetchEmployees(employee)
        loadEmployees()

        return view
    }

    private fun loadEmployees() {
        val progress= ProgressDialog(requireActivity())

        viewModel.employeeListState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading ->  progress.show(requireActivity().window)
                is Resource.Success -> {
                    progress.dismiss(requireActivity().window)
                    resource.data?.data?.let { adapter.submitList(it) }
                }
                is Resource.Error -> {
                    progress.dismiss(requireActivity().window)

                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
