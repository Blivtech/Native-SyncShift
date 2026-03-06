package com.blivtech.syncshift.ui.company

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blivtech.syncshift.data.model.response.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val repository: CompanyRepository) : ViewModel() {

    val companyList: LiveData<List<CompanyEntity>> =
        repository.getCompanies()



    private val _shiftList = MutableLiveData<List<ShiftItem>>()
    val shiftList: LiveData<List<ShiftItem>> = _shiftList

    private val _saveResult = MutableLiveData<UiState<Boolean>>()
    val saveResult: LiveData<UiState<Boolean>> = _saveResult

    init {
        generateRandomShifts()
    }

    fun generateRandomShifts() {

        val shifts = listOf(
            ShiftItem("General Shift", "GS01", "09:00 AM - 05:00 PM"),
            ShiftItem("First Shift", "FS01", "06:00 AM - 02:00 PM"),
            ShiftItem("Second Shift", "SS01", "02:00 PM - 10:00 PM"),
            ShiftItem("Third Shift", "TS01", "10:00 PM - 06:00 AM"),
            ShiftItem("Morning Shift", "MS01", "08:00 AM - 12:00 PM"),
            ShiftItem("Night Shift", "NS01", "08:00 PM - 04:00 AM")
        )

        val randomList = shifts.shuffled(Random(System.currentTimeMillis()))

        _shiftList.value = randomList
    }


        fun saveComapanyDetails(data:CompanySaveRequestItem){


        }

}