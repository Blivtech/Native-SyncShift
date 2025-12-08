package com.blivtech.syncshift.ui.home.fragment.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blivtech.syncshift.R
import com.blivtech.syncshift.data.model.GridItem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(  @ApplicationContext private val context: Context):ViewModel() {

    private val _items = MutableLiveData<List<GridItem>>()
    val items: LiveData<List<GridItem>> get() = _items



    fun loadMenuItems() {

        val list = mutableListOf<GridItem>()


        list += listOf(
            GridItem(R.drawable.img_attendance,context.getString(R.string.menu_add_employee)),
            GridItem(R.drawable.img_advance,context.getString(R.string.menu_advance)),
            GridItem(R.drawable.img_employee_document,context.getString(R.string.menu_employee_document)),
            GridItem(R.drawable.img_overtime,context.getString(R.string.menu_overtime)),
            GridItem(R.drawable.img_salary,context.getString(R.string.menu_salary)),
            GridItem(R.drawable.img_inactive_employees,context.getString(R.string.menu_inactive_employee)),
            GridItem(R.drawable.img_missed_dates,context.getString(R.string.menu_missed_date)),
            GridItem(R.drawable.img_edit,context.getString(R.string.menu_edit)),
            GridItem(R.drawable.img_edit,context.getString(R.string.menu_logout)),


            )

        _items.value = list
    }


}