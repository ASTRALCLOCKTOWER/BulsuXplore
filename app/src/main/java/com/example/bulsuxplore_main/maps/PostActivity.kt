package com.example.bulsuxplore_main.maps


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bulsuxplore_main.R
import com.example.bulsuxplore_main.databinding.ActivityPostBinding
import com.example.bulsuxplore_main.users.EventData
import com.example.bulsuxplore_main.users.UserData
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Integer.parseInt
import java.lang.Long.parseLong
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class PostActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {



    private lateinit var dbRef: DatabaseReference
    private lateinit var dbRefUsers: DatabaseReference
    private lateinit var binding: ActivityPostBinding
    private lateinit var currentUser: FirebaseUser


    private var userName:String?= null
    private var month_name:String?=null
    private var date:String?=null
    private var time:String?=null
    private var time24:String?=null
    private var eventStamp:Long?= null

    var day= 0
    var month= 0
    var year= 0
    var hour= 0
    var minute=0

    var savedDay= 0
    var savedMonth= 0
    var savedYear= 0
    var savedHour= 0
    var savedMinute=0

    var savedHourInt=0

    var AM_PM:String?=null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabSend.setOnClickListener{
            saveContentData()
            onClick()
        }
        getUser()
        pickDate()

    }

    private fun saveContentData(){

        dbRef = FirebaseDatabase.getInstance().getReference( "events")



        val eventTitle = binding.editTextSetTitle.text.toString()
        val location = binding.editTextSetLocation.text.toString()
        val description = binding.editTextSetDesc.text.toString()
        val userName = userName
        val eventId = dbRef.push().key!!
        val events = EventData(eventId, userName,eventTitle,location,date,year,month,day,time,savedHourInt,minute,AM_PM,description,eventStamp)




//         if(eventTitle.isEmpty()){
//                    binding.editTextSetTitle.error= " Write a Title "
//                }
//
//                if(description.isEmpty()){
//                    binding.editTextSetDesc.error= " No content "
//                }
//                if(time.isEmpty()){
//                    binding.edit.error= " Set time "
//                }
//                if(location.isEmpty()){
//                    binding.editTextSetLocation.error= " Set location "
//                }
//
//                if(date.isEmpty()){
//                    binding.editTextSetDate.error= " Write a Title "



        dbRef.child(eventId).setValue(events)
            .addOnCompleteListener{
                Toast.makeText(this, "Posted", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show()
            }


    }
    private fun getUser(){

        dbRefUsers = FirebaseDatabase.getInstance().getReference("users")

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            dbRefUsers.child(currentUser.uid).addListenerForSingleValueEvent(object:
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(UserData::class.java)

                    if (user != null && user.fullName?.isNotEmpty() == true) {
                        userName = "${user.fullName}"
                    } else{
                        userName = "Visitor"
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    // check ano problem pag di nag work
                    Log.e("profile_fragment", "Database error: ${databaseError.message}")
                }
            })
        }

    }
    private fun getDateTimeCalendar(){
        val cal= Calendar.getInstance()
        day=cal.get(Calendar.DAY_OF_MONTH)
        month=cal.get(Calendar.MONTH)
        year=cal.get(Calendar.YEAR)
        hour=cal.get(Calendar.HOUR_OF_DAY)
        minute=cal.get(Calendar.MINUTE)

    }

    private fun pickDate(){

        binding.ButtonDate.setOnClickListener{
            getDateTimeCalendar()
            DatePickerDialog(this,this, year,month,day).show()
        }
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        savedDay= dayOfMonth
        savedMonth= month
        savedYear=year


        val cal = Calendar.getInstance()
        val month_date = SimpleDateFormat("MMMM")

        cal[Calendar.MONTH] = savedMonth
        month_name = month_date.format(cal.time)



        getDateTimeCalendar()
        TimePickerDialog(this,this,hour,minute,false).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour= hourOfDay
        savedMinute= minute


        val stringMonth = String.format("%02d", savedMonth)
        val stringDay = String.format("%02d", savedDay)
        val stringMinutes = String.format("%02d", savedMinute)
        val stringHour = String.format("%02d", savedHour)

        AM_PM = if (hourOfDay < 12) {
            "AM"
        } else {
            "PM"
        }

        val stringEventStamp= parseLong("$savedYear$stringMonth$stringDay$stringHour$stringMinutes")
        eventStamp= stringEventStamp
        date= "$month_name $savedDay, $savedYear"
        time24= "$savedHour:$stringMinutes"
        time=convert24To12HourFormat(time24!!)
        val savedHourStr= convertHour(time!!)
        val savedHourInt = savedHourStr.toIntOrNull()

        binding.ButtonDate.text= "$date; $time "

    }

    fun convert24To12HourFormat(time24: String): String {
        val sdf24 = SimpleDateFormat("HH:mm", Locale.getDefault())
        val sdf12 = SimpleDateFormat("hh:mm a", Locale.getDefault())

        val dateObj = sdf24.parse(time24)
        return sdf12.format(dateObj)
    }

    fun convertHour(time: String): String {
        val hour12 = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val hour24 = SimpleDateFormat("hh", Locale.getDefault())

        val dateObj = hour12.parse(time)
        return hour24.format(dateObj)
    }

    private fun onClick(){
        startActivity(Intent(this@PostActivity, MainActivity::class.java))
    }

    override fun onBackPressed() {
        startActivity(Intent(this@PostActivity, MainActivity::class.java))
        super.onBackPressed()
    }

    private fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }





}










