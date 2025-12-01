package com.blivtech.syncshift.ui.view.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.blivtech.syncshift.R
import com.blivtech.syncshift.databinding.ActivityAllEmployeeBinding
import com.blivtech.syncshift.ui.adapter.EmployeeAdapter
import com.blivtech.syncshift.ui.viewModel.EmployeeViewModel

class AllEmployeeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllEmployeeBinding
    private val viewModel: EmployeeViewModel by viewModels()

    private lateinit var adapter: EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAllEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycler()

        viewModel.employeeListLiveData.observe(this) {
            adapter.updateList(it)
        }

        viewModel.loadEmployees()

        setupFilters()
        setupSearch()

        binding.btnSave.setOnClickListener {
            showSavePopup()
        }
    }

    private fun setupRecycler() {

        adapter = EmployeeAdapter(mutableListOf()) { emp, status ->
            viewModel.updateStatus(emp, status)
        }

        binding.rvEmployees.layoutManager = LinearLayoutManager(this)
        binding.rvEmployees.adapter = adapter
    }

    private fun setupFilters() {

        binding.btnAll.setOnClickListener {
            viewModel.currentFilter = "All"
            viewModel.applyFilter()
        }

        binding.btnPresent.setOnClickListener {
            viewModel.currentFilter = "Present"
            viewModel.applyFilter()
        }

        binding.btnAbsent.setOnClickListener {
            viewModel.currentFilter = "Absent"
            viewModel.applyFilter()
        }
    }

    private fun setupSearch() {

        binding.searchView.setOnQueryTextListener(
            object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = true

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.search(newText ?: "")
                    return true
                }
            }
        )
    }
    private fun showSavePopup() {

        val dialogView = layoutInflater.inflate(R.layout.popup_save_attendance, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        val tvPresent = dialogView.findViewById<TextView>(R.id.tvPresentCount)
        val tvAbsent = dialogView.findViewById<TextView>(R.id.tvAbsentCount)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
        val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirm)

        tvPresent.text = "Present: ${viewModel.getPresentCount()}"
        tvAbsent.text = "Absent: ${viewModel.getAbsentCount()}"

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this, "Attendance Saved Successfully!", Toast.LENGTH_SHORT).show()
        }

        dialog.show()
    }
}