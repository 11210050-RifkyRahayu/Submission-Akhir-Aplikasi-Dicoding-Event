package com.example.dicodingevent.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.ApiConfig
import com.example.dicodingevent.data.EventResponseList
import com.example.dicodingevent.data.ListEventsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _event = MutableLiveData<List<ListEventsItem>>()
    val event: LiveData<List<ListEventsItem>> = _event

    private val _error = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _error

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    companion object {
        private const val EVENT_ID = 0
    }

    fun loadActiveEvents() {
        _isLoading.value = true
        val clinet = ApiConfig.getApiService().getEventsactive(EVENT_ID)
        clinet.enqueue(object : Callback<EventResponseList> {
            override fun onResponse(
                call: Call<EventResponseList>,
                response: Response<EventResponseList>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _event.value = response.body()?.listEvents
                } else {
                    _error.value = true
                    _message.value = "Failed to load data"
                }
            }

            override fun onFailure(call: Call<EventResponseList>, t: Throwable) {
                _isLoading.value = false
                _error.value = true
                _message.value = t.message ?: "Unknown error"
            }

        })
    }
}