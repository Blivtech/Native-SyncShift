package com.blivtech.syncshift.ui.attendance

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.blivtech.syncshift.data.enumi.AttendanceStatus
import com.blivtech.syncshift.data.model.request.EmployeeAttendanceRequest
import com.blivtech.syncshift.databinding.ActivityAttendanceSelectionBinding
import com.blivtech.syncshift.ui.BaseActivity
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AttendanceSelectionActvity: BaseActivity() {

    private lateinit var binding: ActivityAttendanceSelectionBinding
    private val viewModel: AttendanceViewModel by viewModels()

    private lateinit var adapter: EmployeeAttendanceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
       applyDisplayCutout(binding.main)
        setupRecyclerView()
        observeData()
        setupTabs()
        setToolbar()

        setOnClick()
    }

    private fun setOnClick() {

        binding.btnSubmit.setOnClickListener {
            lifecycleScope.launch {
                val requestList = adapter.getAttendanceList()
                    .filter { it.status == AttendanceStatus.PRESENT || it.status == AttendanceStatus.ABSENT }
                    .map {
                        EmployeeAttendanceRequest(
                            employeeName = it.name,
                            employeeCode = it.employeeId,
                            status = it.status.name
                        )
                    }

            viewModel.insertAttendanceData(requestList)
                finish()
            }


        }
    }

    private fun setToolbar() {
        binding.toolbar.tvTittle.text="Section Activity"
    }

    private fun setupRecyclerView() {
        adapter = EmployeeAttendanceAdapter { data, status ->
            viewModel.markAttendance(data, status)
        }

        binding.rvAttendance.layoutManager = LinearLayoutManager(this)
        binding.rvAttendance.adapter = adapter
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.attendanceList.collect { list ->
                adapter.submitList(list)
            }
        }
    }
    private fun setupTabs() {
        binding.tabLayout.apply {
            addTab(newTab().setText("Not In Yet"))
            addTab(newTab().setText("Present"))
            addTab(newTab().setText("Absent"))

            binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (tab.position) {
                        0 -> adapter.filterByTab(AttendanceStatus.NOT_IN_YET)
                        1 -> adapter.filterByTab(AttendanceStatus.PRESENT)
                        2 -> adapter.filterByTab(AttendanceStatus.ABSENT)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }
}
