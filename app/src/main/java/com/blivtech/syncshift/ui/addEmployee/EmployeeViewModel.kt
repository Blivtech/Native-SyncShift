package com.blivtech.syncshift.ui.addEmployee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blivtech.syncshift.data.model.response.UiState
import com.blivtech.syncshift.data.model.local.Entity.EmployeeEntity
import com.blivtech.syncshift.domain.usecase.AddEmployeeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val addEmployeeUseCase: AddEmployeeUseCase
) : ViewModel() {

    private val _employeeState = MutableLiveData<UiState<Boolean>>()
    val employeeState: LiveData<UiState<Boolean>> get() = _employeeState


    private val _employeeSyncState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val employeeSyncState: StateFlow<UiState<Boolean>> = _employeeSyncState


    private val employeesFlow: StateFlow<List<EmployeeEntity>> =
        addEmployeeUseCase.observeEmployees()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )



    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    val filteredEmployeeList: StateFlow<List<EmployeeEntity>> =
        combine(
            employeesFlow,
            searchQuery
        ) { employees, query ->
            if (query.isBlank()) {
                employees
            } else {
                employees.filter {
                    it.employeeName.contains(query, true) ||
                            it.employeeCode.contains(query, true) ||
                            it.mobileNumber.contains(query, true) ||
                            it.designation.contains(query, true)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )




    fun addEmployee(employee: EmployeeEntity) {
        viewModelScope.launch {
            _employeeState.value = UiState.Loading
            _employeeState.value = addEmployeeUseCase.save(employee)
        }
    }



    fun fetchEmployees(companyCode: String) {
        viewModelScope.launch {
            _employeeSyncState.value = UiState.Loading
            _employeeSyncState.value = addEmployeeUseCase.get(companyCode)
        }
    }
}
