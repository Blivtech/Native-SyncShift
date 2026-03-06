package com.blivtech.syncshift.ui.attendance

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.blivtech.syncshift.R
import com.blivtech.syncshift.data.enumi.DayPlanType
import com.blivtech.syncshift.data.enumi.DurationType
import com.blivtech.syncshift.data.model.request.DayPlanRequest
import com.blivtech.syncshift.data.model.response.ShiftTiming
import com.blivtech.syncshift.databinding.ActivityAttendanceBinding
import com.blivtech.syncshift.ui.BaseActivity
import com.blivtech.syncshift.ui.bottomsheet.ShiftTimingBottomSheet
import com.blivtech.syncshift.utils.CommonClass
import com.blivtech.syncshift.utils.SharedPreferencesManager
import com.blivtech.syncshift.utils.TimeUtils
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class AttendanceActivity : BaseActivity() {

    private lateinit var binding: ActivityAttendanceBinding
    private val viewModel: AttendanceViewModel by viewModels()

    private var selectedDayPlan: DayPlanType = DayPlanType.WORKING_DAY
    private var selectedDuration: DurationType = DurationType.FULL_DAY
    private lateinit var shiftTiming: ShiftTiming

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyDisplayCutout(binding.main)

        setupDayPlanClicks()
        setupDurationClicks()
        setupShiftClick()
        setupPresenceClick()

        binding.toolbar.tvTittle.text="Attendance"
        selectDayPlan(binding.cardWorkingDay, DayPlanType.WORKING_DAY)

    }

    private fun setupPresenceClick() {
        binding.cardMovePresence.setOnClickListener {
            CommonClass.launchActivity(this, AttendanceSelectionActvity::class.java)
        }
        binding.btnSubmit.setOnClickListener { submit() }
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


    private fun setupShiftClick() = with(binding) {
        binding.cardShiftType.setOnClickListener {
            ShiftTimingBottomSheet { shift ->
                binding.tvShiftName.text = shift.name
                shiftTiming=shift
            }.show(supportFragmentManager, "ShiftTimingBottomSheet")
        }
    }


    private fun submit() {
        if(shiftTiming==null){
            Toast.makeText(this, "Select Shift", Toast.LENGTH_SHORT).show()
            return
        }
        if (selectedDayPlan == null) {
            Toast.makeText(this, "Select Day Plan", Toast.LENGTH_SHORT).show()
            return
        }
       val userData=SharedPreferencesManager.getLoginData(context = this)
        val requestData = DayPlanRequest(
            planid ="${userData.btCode}-${TimeUtils.getCurrentDateTime(TimeUtils.FORMAT_21)}-${shiftTiming.code}" ,
            btcode = userData.btCode,
            activityDate = TimeUtils.getCurrentDateTime(TimeUtils.FORMAT_5),
            shiftCode = shiftTiming.code,
            shiftName = shiftTiming.name,
            workPlan = selectedDayPlan.label,
            workplanCode = selectedDayPlan.code,
            durationType = selectedDuration.label,
            durationCode = selectedDuration.code,
            Remark = binding.etRemark.text.toString(),
            AppMode =getString(R.string.app_mode),
            AppVersion = getString(R.string.app_version_number),
            DeviceName = CommonClass.getDeviceName(),
            created_by =userData.btCode,
            attendance =emptyList()
        )
        viewModel.submitAttendance(requestData)

    }
}