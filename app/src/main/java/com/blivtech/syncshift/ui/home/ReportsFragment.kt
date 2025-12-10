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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportsFragment : Fragment() {

    private val viewModel: EmployeeViewModel by viewModels()
    private lateinit var adapter: EmployeeListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reports, container, false)

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
