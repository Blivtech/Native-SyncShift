package com.blivtech.syncshift.ui.addEmployee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blivtech.syncshift.data.model.response.Resource
import com.blivtech.syncshift.data.model.local.Entity.EmployeeEntity
import com.blivtech.syncshift.data.model.request.EmployeeRequest
import com.blivtech.syncshift.data.model.response.AddEmployeeResponse
import com.blivtech.syncshift.domain.usecase.AddEmployeeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val addEmployeeUseCase: AddEmployeeUseCase
) : ViewModel() {

    /* -------------------- ADD EMPLOYEE STATE -------------------- */

    private val _employeeState = MutableLiveData<Resource<AddEmployeeResponse>>()
    val employeeState: LiveData<Resource<AddEmployeeResponse>> get() = _employeeState


    /* -------------------- SYNC STATE -------------------- */

    private val _employeeSyncState =
        MutableStateFlow<Resource<Unit>>(Resource.Loading())
    val employeeSyncState: StateFlow<Resource<Unit>> = _employeeSyncState


    /* -------------------- EMPLOYEE LIST (ROOM) -------------------- */

    private val employeesFlow: StateFlow<List<EmployeeEntity>> =
        addEmployeeUseCase.observeEmployees()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )


    /* -------------------- SEARCH -------------------- */

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
                    it.employee_name.contains(query, true) ||
                            it.employee_id.contains(query, true) ||
                            it.phone.contains(query, true) ||
                            it.designation.contains(query, true)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )



    /* -------------------- ADD EMPLOYEE -------------------- */

    fun addEmployee(employee: EmployeeRequest) {
        viewModelScope.launch {
            _employeeState.value = Resource.Loading()
            _employeeState.value = addEmployeeUseCase.save(employee)
        }
    }


    /* -------------------- FETCH / SYNC EMPLOYEES -------------------- */

    fun fetchEmployees(btCode: String) {
        viewModelScope.launch {
            _employeeSyncState.value = Resource.Loading()
            _employeeSyncState.value = addEmployeeUseCase.get(btCode)
        }
    }
}
