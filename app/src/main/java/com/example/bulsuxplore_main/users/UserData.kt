package com.example.bulsuxplore_main.users

import androidx.compose.foundation.pager.PageSize.Fill.calculateMainAxisPageSize
import com.google.type.Date


// this is a class where we declare the information we need from the users //
data class UserData(
    val uid: String? = null,
    val fullName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val password: String? = null

)
data class EventData(
    val eventId:String? =null,
    val userName:String?= null,
    var eventTitle:String?=null,
    var location:String?=null,
    var date:String?=null,
    var year:Int?=null,
    var month:Int?=null,
    var day:Int?=null,
    var time:String?=null,
    var hour:Int?=null,
    var minute:Int?=null,
    var amPm:String?=null,
    var description:String? = null,
    var eventStamp:Long?=null,
    var icon:String?="⋮"


    )

data class ArchiveData(

    val eventId:String? =null,
    val userName:String?= null,
    val eventTitle:String?=null,
    val location:String?=null,
    val date:String?=null,
    val time:String?=null,
    val description:String? = null,
    val eventStamp:Long?= null


)


