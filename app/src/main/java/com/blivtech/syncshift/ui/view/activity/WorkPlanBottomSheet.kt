package com.blivtech.syncshift.ui.view.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blivtech.syncshift.databinding.BottomsheetWorkTypeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class WorkPlanBottomSheet:BottomSheetDialogFragment() {

    private var _binding: BottomsheetWorkTypeBinding? = null
    private val binding get() = _binding!!
    interface WorkTypeSelectListener {
        fun onWorkTypeSelected(type: String)
    }

    private var listener: WorkTypeSelectListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? WorkTypeSelectListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomsheetWorkTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvWorkingDay.setOnClickListener {
            listener?.onWorkTypeSelected("Working Day")
            dismiss()
        }

        binding.tvLeave.setOnClickListener {
            listener?.onWorkTypeSelected("Leave")
            dismiss()
        }

        binding.tvWeekOff.setOnClickListener {
            listener?.onWorkTypeSelected("Week Off")
            dismiss()
        }

        binding.tvHoliday.setOnClickListener {
            listener?.onWorkTypeSelected("Holiday")
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}