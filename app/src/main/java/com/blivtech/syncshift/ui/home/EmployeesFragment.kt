package com.blivtech.syncshift.ui.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmployeesFragment : Fragment() {


    private val viewModel: EmployeeViewModel by viewModels()
    private lateinit var adapter: EmployeeListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_employees, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = EmployeeListAdapter()
        recyclerView.adapter = adapter

        viewModel.fetchEmployees(SharedPreferencesManager.getLoginData(requireContext()).bt_code)
        syncEmployeeStatus()

        return view
    }
    private fun syncEmployeeStatus() {
        val progress = ProgressDialog(requireActivity())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

//                // Collect sync state
//                launch {
//                    viewModel.employeeSyncState.collect { resource ->
//                        when (resource) {
//                            is Resource.Loading -> progress.show(requireActivity().window)
//                            is Resource.Success -> progress.dismiss(requireActivity().window)
//                            is Resource.Error -> {
//                                progress.dismiss(requireActivity().window)
//                                Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    }
//                }

                // Collect employee list
                launch {
                    viewModel.employeeList.collect { list ->
                        adapter.submitList(list)
                    }
                }
            }
        }
    }


}
