package com.blivtech.syncshift.ui.attendance

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class AttendanceActivity : BaseActivity() {

    private lateinit var binding: ActivityAttendanceBinding
    private val viewModel: AttendanceViewModel by viewModels()

    private var selectedDayPlan: DayPlanType = DayPlanType.WORKING_DAY
    private var selectedDuration: DurationType = DurationType.FULL_DAY
    private lateinit var  shiftCode:String
    private lateinit var  shiftName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyDisplayCutout(binding.main)
        setupShiftData()
        setupDayPlanClicks()
        setupDurationClicks()
        setupPresenceClick()
        observe()
        binding.toolbar.tvTittle.text="Attendance"
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
            selectDuration(DurationType.FULL_DAY.code)
        }

        tvHalfDay.setOnClickListener {
            selectDuration(DurationType.HALF_DAY.code)
        }
    }

    private fun selectDuration(type: String) = with(binding) {

        if (type == DurationType.FULL_DAY.code) {
            tvFullDay.setBackgroundResource(R.drawable.bg_segment_selected)
            tvFullDay.setTextColor(getColor(R.color.button_color))

            tvHalfDay.background = null
            tvHalfDay.setTextColor(getColor(R.color.text_gray))
            selectedDuration=DurationType.FULL_DAY
        } else {
            tvHalfDay.setBackgroundResource(R.drawable.bg_segment_selected)
            tvHalfDay.setTextColor(getColor(R.color.button_color))

            tvFullDay.background = null
            tvFullDay.setTextColor(getColor(R.color.text_gray))
            selectedDuration=DurationType.HALF_DAY
        }
    }

    private fun setupShiftData() {
        val bundle = intent.extras
        val activityDate = bundle?.getString("activityDate")?:""
         shiftCode = bundle?.getString("shiftCode")?:""
         shiftName = bundle?.getString("shiftName")?:""

        binding.tvShiftName.text=shiftName

         viewModel.insertNewDayPlan(shiftCode,shiftName,activityDate)
    }


    private fun submit() {

        if (selectedDayPlan.name == null) {
            Toast.makeText(this, "Select Day Plan", Toast.LENGTH_SHORT).show()
            return
        }
       val userData=SharedPreferencesManager.getLoginData(context = this)
       val companyCode=SharedPreferencesManager.getActiveCompanyCode(context = this)
       val companyName=SharedPreferencesManager.getActiveCompanyName(context = this)
        val requestData = DayPlanRequest(
            planId ="${userData.btCode}-${TimeUtils.getCurrentDateTime(TimeUtils.FORMAT_21)}-${shiftCode}" ,
            btCode = userData.btCode,
            activityDate = TimeUtils.getCurrentDateTime(TimeUtils.FORMAT_5),
            shiftCode = shiftCode,
            shiftName = shiftName,
            companyCode = companyCode,
            companyName = companyName,
            workplanName = selectedDayPlan.label,
            workplanCode = selectedDayPlan.code,
            workplanFlag = selectedDayPlan.code,
            dayType = selectedDuration.code,
            remarks = binding.etRemark.text.toString(),
            mode =getString(R.string.app_mode),
            submittedAt =TimeUtils.getCurrentDateTime(TimeUtils.FORMAT_1),
            attendanceDetails =emptyList()
        )
        viewModel.saveDayPlan(requestData)

    }


    private fun observe(){

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dayPlanDetails.collect { data ->
                    if (data != null) {
                        shiftCode=data.shiftCode
                        shiftName=data.shiftName
                        selectDuration(data.duration)
                        when (data.planCode) {
                            "WD" -> selectDayPlan(binding.cardWorkingDay, DayPlanType.WORKING_DAY)
                            "H" -> selectDayPlan(binding.cardHoliday, DayPlanType.HOLIDAY)
                            "WO" -> selectDayPlan(binding.cardWeeklyOff, DayPlanType.WEEKLY_OFF)
                            "L" -> selectDayPlan(binding.cardLeave, DayPlanType.LEAVE)
                        }


                        binding.txtAbsentCount.text= data.absentCount.toString()
                        binding.txtPresentCount.text= data.presentCount.toString()
                    }

                }
            }
        }
    }
}