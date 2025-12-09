package com.blivtech.syncshift.ui.addEmployee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.blivtech.syncshift.data.model.Resource
import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.data.model.response.AddEmployeeResponse
import com.blivtech.syncshift.data.model.response.GetEmployeeListResponse
import com.blivtech.syncshift.domain.usecase.AddEmployeeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val addEmployeeUseCase: AddEmployeeUseCase
) : ViewModel() {

    private val _employeeState = MutableLiveData<Resource<AddEmployeeResponse>>()
    val employeeState: LiveData<Resource<AddEmployeeResponse>> get() = _employeeState
    private val _employeeListState = MutableLiveData<Resource<GetEmployeeListResponse>>()
    val employeeListState: LiveData<Resource<GetEmployeeListResponse>> get() = _employeeListState


    fun addEmployee(employee: EmployeeRequest) {
        viewModelScope.launch {
            _employeeState.value = Resource.Loading()

            val result = addEmployeeUseCase.save(employee)
            _employeeState.value = result
        }
    }


    fun fetchEmployees(employee: EmployeeRequest) {
        viewModelScope.launch {
            _employeeListState.value = Resource.Loading()
            val result = addEmployeeUseCase.get(employee)
            _employeeListState.value = result
        }
    }
}
