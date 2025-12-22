package com.blivtech.syncshift.ui.attendance

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.blivtech.syncshift.R
import com.blivtech.syncshift.data.enumi.DayPlanType
import com.blivtech.syncshift.data.enumi.DurationType
import com.blivtech.syncshift.databinding.ActivityAttendanceBinding
import com.blivtech.syncshift.ui.BaseActivity
import com.blivtech.syncshift.ui.bottomsheet.ShiftTimingBottomSheet
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttendanceActivity : BaseActivity() {

    private lateinit var binding: ActivityAttendanceBinding

    private var selectedDayPlan: DayPlanType? = DayPlanType.WORKING_DAY
    private var selectedDuration: DurationType = DurationType.FULL_DAY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyDisplayCutout(binding.main)

        setupDayPlanClicks()
        setupDurationClicks()
        setupShiftClick()

        binding.toolbar.tvTittle.text="Attendance"
        selectDayPlan(binding.cardWorkingDay, DayPlanType.WORKING_DAY)

    }


    private fun setupDayPlanClicks() = with(binding) {

        cardWorkingDay.setOnClickListener {
            selectDayPlan(cardWorkingDay, DayPlanType.WORKING_DAY)
        }

        cardHoliday.setOnClickListener {
            selectDayPlan(cardHoliday, DayPlanType.HOLIDAY)
        }

        cardWeeklyOff.setOnClickListener {
            selectDayPlan(cardWeeklyOff, DayPlanType.WEEKLY_OFF)
        }

        cardLeave.setOnClickListener {
            selectDayPlan(cardLeave, DayPlanType.LEAVE)
        }

    }

    private fun selectDayPlan(
        selectedCard: MaterialCardView,
        type: DayPlanType
    ) {
        resetDayPlanCards()

        selectedDayPlan = type

        selectedCard.strokeWidth = 2
        selectedCard.strokeColor =
            ContextCompat.getColor(this, R.color.button_color)
    }

    private fun resetDayPlanCards() = with(binding) {
        val defaultColor =
            ContextCompat.getColor(this@AttendanceActivity, R.color.text_gray)

        listOf(
            cardWorkingDay,
            cardHoliday,
            cardWeeklyOff,
            cardLeave
        ).forEach {
            it.strokeWidth = 1
            it.strokeColor = defaultColor
        }
    }


    private fun setupDurationClicks() = with(binding) {

        tvFullDay.setOnClickListener {
            selectDuration(DurationType.FULL_DAY)
        }

        tvHalfDay.setOnClickListener {
            selectDuration(DurationType.HALF_DAY)
        }
    }

    private fun selectDuration(type: DurationType) = with(binding) {

        selectedDuration = type

        if (type == DurationType.FULL_DAY) {
            tvFullDay.setBackgroundResource(R.drawable.bg_segment_selected)
            tvFullDay.setTextColor(getColor(R.color.button_color))

            tvHalfDay.background = null
            tvHalfDay.setTextColor(getColor(R.color.text_gray))
        } else {
            tvHalfDay.setBackgroundResource(R.drawable.bg_segment_selected)
            tvHalfDay.setTextColor(getColor(R.color.button_color))

            tvFullDay.background = null
            tvFullDay.setTextColor(getColor(R.color.text_gray))
        }
    }

    /* ---------------- SHIFT TYPE ---------------- */

    private fun setupShiftClick() = with(binding) {
        binding.cardShiftType.setOnClickListener {
            ShiftTimingBottomSheet { shift ->
                binding.tvShiftName.text = shift.name
            }.show(supportFragmentManager, "ShiftTimingBottomSheet")
        }
    }

    /* ---------------- GET VALUES ---------------- */

    private fun submit() {
        if (selectedDayPlan == null) {
            Toast.makeText(this, "Select Day Plan", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("DayPlan",
            "DayPlan = $selectedDayPlan, Duration = $selectedDuration"
        )

        // API / DB / ViewModel call here
    }
}