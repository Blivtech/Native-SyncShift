package com.blivtech.syncshift.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DemoViewModel(): ViewModel() {


    private  val _addtion = MutableLiveData<Int>()
    val addition : LiveData<Int> get() =_addtion



    fun addition(a: Int,b: Int){
        _addtion.value=a+b
    }

}