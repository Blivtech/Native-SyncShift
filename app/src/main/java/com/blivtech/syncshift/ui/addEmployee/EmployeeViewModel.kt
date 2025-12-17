package com.blivtech.syncshift.ui.addEmployee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.blivtech.syncshift.data.model.Resource
import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.data.model.response.AddEmployeeResponse
import com.blivtech.syncshift.domain.usecase.AddEmployeeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class EmployeeViewModel @Inject constructor(private val addEmployeeUseCase: AddEmployeeUseCase) : ViewModel() {


    private val _employeeSyncState = MutableStateFlow<Resource<Unit>>(Resource.Loading())
    val employeeSyncState: StateFlow<Resource<Unit>> = _employeeSyncState


    private val _employeeState = MutableLiveData<Resource<AddEmployeeResponse>>()
    val employeeState: LiveData<Resource<AddEmployeeResponse>> get() = _employeeState


    val employeeList = addEmployeeUseCase.observeEmployees()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addEmployee(employee: EmployeeRequest) {
        viewModelScope.launch {
            _employeeState.value = Resource.Loading()

            val result = addEmployeeUseCase.save(employee)
            _employeeState.value = result
        }
    }
    fun fetchEmployees(btcode: String) {
        viewModelScope.launch {
            _employeeSyncState.value = Resource.Loading()
            val result = addEmployeeUseCase.get(btcode)
            _employeeSyncState.value = result
        }
    }


}
