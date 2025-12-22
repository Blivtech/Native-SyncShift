package com.blivtech.syncshift.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.blivtech.syncshift.R
import com.blivtech.syncshift.data.model.ShiftTiming
import com.blivtech.syncshift.databinding.BottomSheetShiftTimingBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ShiftTimingBottomSheet(
    private val onApply: (ShiftTiming) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetShiftTimingBinding
    private var selectedShift: ShiftTiming? = null

    private val shifts = mutableListOf(
        ShiftTiming(1, "General Shift", "09:00 AM - 06:00 PM", true),
        ShiftTiming(2, "First Shift", "06:00 AM - 02:00 PM"),
        ShiftTiming(3, "Second Shift", "02:00 PM - 10:00 PM"),
        ShiftTiming(4, "Night Shift", "10:00 PM - 06:00 AM")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetShiftTimingBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        selectedShift = shifts.first { it.isSelected }

        binding.rvShift.layoutManager = LinearLayoutManager(requireContext())
        binding.rvShift.adapter =
            ShiftTimingAdapter(shifts) { selectedShift = it }

        binding.btnClose.setOnClickListener { dismiss() }

        binding.btnApply.setOnClickListener {
            selectedShift?.let {
                onApply(it)
                dismiss()
            }
        }
    }
}
