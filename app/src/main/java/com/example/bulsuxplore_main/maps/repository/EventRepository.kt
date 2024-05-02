package com.example.bulsuxplore_main.maps.repositor

import androidx.lifecycle.MutableLiveData
import com.example.bulsuxplore_main.users.EventData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class EventRepository {

    private val dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("events")
    @Volatile private var INSTANCE : EventRepository?= null

    fun getInstance() : EventRepository{
        return INSTANCE?: synchronized(this){
            val instance = EventRepository()
            INSTANCE= instance
            instance
        }
    }

    fun loadEvents(eventList: MutableLiveData<List<EventData>>) {

        dbRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //change in data

                try{
                    val _eventList: List<EventData> = snapshot.children.map {
                        dataSnapshot -> dataSnapshot.getValue(EventData::class.java)!!
                    }

                    val  sortedEventList= _eventList.sortedBy{ it.eventStamp }

                    eventList.postValue(sortedEventList)


                } catch (e: Exception){}
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }




}