package com.blivtech.syncshift.ui.company

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blivtech.syncshift.data.model.response.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val repository: CompanyRepository) : ViewModel() {

    val companyList: LiveData<List<CompanyEntity>> =
        repository.getCompanies()



    private val _shiftList = MutableLiveData<List<ShiftEntity>>()
    val shiftList: LiveData<List<ShiftEntity>> = _shiftList

    private val _saveResult = MutableLiveData<UiState<Boolean>>()
    val saveResult: LiveData<UiState<Boolean>> = _saveResult

    init {
        generateRandomShifts()
    }

    private fun generateRandomShifts() {

        val shifts = listOf(
            ShiftEntity("General Shift", "","GS01", "09:00:00","05:00:00"),
            ShiftEntity("First Shift", "","FS01", "06:00:00","02:00:00"),
            ShiftEntity("Second Shift","", "SS01", "02:00:00","10:00:00"),
            ShiftEntity("Third Shift", "","TS01", "10:00:00","06:00:00"),
            ShiftEntity("Morning Shift","", "MS01", "08:00:00","12:00:00"),
            ShiftEntity("Night Shift", "","NS01", "08:00:00","04:00:00")
        )

        _shiftList.value = shifts
    }


        fun saveCompany(data:CompanySaveRequestItem){
            viewModelScope.launch {
                _saveResult.value= repository.saveCompany(data)}
        }

}