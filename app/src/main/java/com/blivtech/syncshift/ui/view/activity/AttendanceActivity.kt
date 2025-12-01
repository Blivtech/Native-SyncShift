package com.blivtech.syncshift.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.blivtech.syncshift.R
import com.blivtech.syncshift.databinding.ActivityAttendanceBinding

class AttendanceActivity : AppCompatActivity(),WorkPlanBottomSheet.WorkTypeSelectListener {


    private lateinit var binding: ActivityAttendanceBinding

    enum class WorkType { WORKING_DAY, LEAVE, WEEK_OFF, HOLIDAY, NONE }

    private var selectedWorkType = WorkType.NONE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvTitle.text = " Day Plan"

        binding.tvDate.text =
            java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
                .format(System.currentTimeMillis())

        binding.etWorkType.setOnClickListener { openWorkTypeSheet() }
        binding.ivDownArrow.setOnClickListener { openWorkTypeSheet() }

        binding.btnDoAttendance.setOnClickListener {
            if (selectedWorkType == WorkType.WORKING_DAY) {
                val intent = Intent(this, AllEmployeeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Select Working Day to do attendance", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSave.setOnClickListener { saveWorkPlan() }

        binding.ivBack.setOnClickListener { finish() }

    }

    private fun openWorkTypeSheet() {
        val sheet = WorkPlanBottomSheet()
        sheet.show(supportFragmentManager, "WorkTypeBottomSheet")
    }

    override fun onWorkTypeSelected(type: String) {
        binding.etWorkType.setText(type)

        when (type) {
            "Working Day" -> {
                selectedWorkType = WorkType.WORKING_DAY
                binding.tvRemarksLabel.visibility = View.GONE
                binding.etRemarks.visibility = View.GONE
                binding.btnDoAttendance.visibility = View.VISIBLE
            }

            "Leave" -> {
                selectedWorkType = WorkType.LEAVE
                binding.tvRemarksLabel.visibility = View.VISIBLE
                binding.etRemarks.visibility = View.VISIBLE
                binding.btnDoAttendance.visibility = View.GONE
            }

            "Week Off" -> {
                selectedWorkType = WorkType.WEEK_OFF
                binding.tvRemarksLabel.visibility = View.VISIBLE
                binding.etRemarks.visibility = View.VISIBLE
            }

            "Holiday" -> {
                selectedWorkType = WorkType.HOLIDAY
                binding.tvRemarksLabel.visibility = View.VISIBLE
                binding.etRemarks.visibility = View.VISIBLE
            }
        }
    }



    private fun saveWorkPlan() {
        when (selectedWorkType) {
            WorkType.WORKING_DAY -> {
                Toast.makeText(this, "Working Day", Toast.LENGTH_SHORT).show()
            }

            WorkType.LEAVE -> {
                if (binding.etRemarks.text.toString().trim().isEmpty()) {
                    Toast.makeText(this, "Please enter remarks", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Leave Saved", Toast.LENGTH_SHORT).show()
                }
            }

            WorkType.WEEK_OFF, WorkType.HOLIDAY -> {
                Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show()
            }

            else -> {
                Toast.makeText(this, "Select Work Type", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


