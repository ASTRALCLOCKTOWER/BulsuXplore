package com.example.bulsuxplore_main.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bulsuxplore_main.maps.repositor.EventRepository
import androidx.lifecycle.ViewModel

class EventViewModel: ViewModel() {

    private val repository : EventRepository
    private val _allEventData = MutableLiveData<List<EventData>>()
    val allEventData: LiveData<List<EventData>> = _allEventData


    init {
        repository = EventRepository().getInstance()
        repository.loadEvents(_allEventData)
    }




}