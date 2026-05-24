package com.blivtech.syncshift.ui.advance

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blivtech.syncshift.data.model.request.AdvanceRequest
import com.blivtech.syncshift.data.repository.AdvanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdvanceViewModel @Inject constructor(private val repository: AdvanceRepository):ViewModel() {

    val responseLiveData = MutableLiveData<String>()

    fun saveAdvance(

        request: List<AdvanceRequest>

    ) {

        viewModelScope.launch {

            try {

                val response =
                    repository.saveAdvance(request)

                if (response.isSuccessful) {

                    responseLiveData.postValue(
                        "Saved Successfully"
                    )

                } else {

                    responseLiveData.postValue(
                        "Failed"
                    )
                }

            } catch (e: Exception) {

                responseLiveData.postValue(
                    e.message.toString()
                )
            }
        }
    }
}




