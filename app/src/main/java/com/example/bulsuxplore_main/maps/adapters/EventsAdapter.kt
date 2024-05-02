package com.example.bulsuxplore_main.maps.adapters

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.bulsuxplore_main.R
import com.example.bulsuxplore_main.maps.EventsFragment
import com.example.bulsuxplore_main.users.ArchiveData
import com.example.bulsuxplore_main.users.EventData
import com.example.bulsuxplore_main.users.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.withTimeoutOrNull
import java.lang.Long
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class EventsAdapter(val c: Context, val eventList: ArrayList<EventData>): RecyclerView.Adapter<EventsAdapter.MyViewHolder>() {


    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var dbRefArc: DatabaseReference

    private lateinit var datepicker :  Button
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.event_item,
            parent,false)

        return MyViewHolder(itemView)


    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = eventList[position]

        holder.tvAuthorName.text = currentitem.userName
        holder.tvEventName.text = currentitem.eventTitle
        holder.tvLocation.text = currentitem.location
        holder.tvEventDate.text = currentitem.date
        holder.tvEventTime.text =currentitem.time
        holder.tvEventDesc.text = currentitem.description
        holder.tvOptions.text = currentitem.icon

        val currentUser = FirebaseAuth.getInstance().currentUser
        val dbRefUsers= FirebaseDatabase.getInstance().getReference("users")
        var creatorName: String? =null



        if (currentUser != null) {
            dbRefUsers.child(currentUser.uid).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(UserData::class.java)
                    if (currentUser != null && user?.fullName == currentitem.userName) {
                        // User is the creator of this event, show admin-specific features
                        holder.tvOptions.visibility = View.VISIBLE
                    } else {
                        // User is not the creator, hide admin-specific features
                        holder.tvOptions.visibility = View.GONE


                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    // check ano problem pag di nag work
                    Log.e("ProfileFragment", "Database error: ${databaseError.message}")
                }

            })
        }




    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    fun updateEventList(eventList: List<EventData>){
        this.eventList.clear()
        this.eventList.addAll(eventList)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvAuthorName: TextView = itemView.findViewById<TextView>(R.id.textView_userName)
        val tvEventName: TextView = itemView.findViewById<TextView>(R.id.textView_EventName)
        val tvLocation: TextView = itemView.findViewById<TextView>(R.id.textView_Location)
        val tvEventDate: TextView = itemView.findViewById<TextView>(R.id.textView_Date)
        val tvEventTime: TextView = itemView.findViewById<TextView>(R.id.textView_Time)
        val tvEventDesc: TextView = itemView.findViewById<TextView>(R.id.textView_Description)
        val tvOptions: TextView = itemView.findViewById<TextView>(R.id.tvOptions) //popupbutton

        init{
            tvOptions.setOnClickListener{popupMenu(it, position)}
        }


        //pop up  menu
        private fun popupMenu (itemView: View, position: Int){
            val currentItem =eventList[position]
            val pos = eventList[adapterPosition] //inner class made it possible to pass eventList
            val popupMenu= PopupMenu(c,itemView)

            //Archiving/delete Data values
            val eventTitle = currentItem.eventTitle.toString()
            val location = currentItem.location.toString()
            val description = currentItem.description.toString()
            val userName = currentItem.userName.toString()
            val date = currentItem.date.toString()
            val year = currentItem.year.toString()
            val month = currentItem.month.toString()
            val day= currentItem.day.toString()
            val time = currentItem.time.toString()
            val hour= currentItem.hour.toString()
            val minute=currentItem.minute.toString()
            val amPm=currentItem.amPm.toString()
            val saveEventId = currentItem.eventId.toString()


            val yearSDF= SimpleDateFormat("yyyy", Locale.getDefault())
//            val monthSDF = SimpleDateFormat("MMMM", Locale.ENGLISH)
//            val daySDF = SimpleDateFormat("dd")
//
//            val hourSDF= SimpleDateFormat("HH",  Locale.getDefault())
//            val minSDF= SimpleDateFormat("mm",  Locale.getDefault())
//            val cal = Calendar.getInstance()
//
//            //format
//            val yearF  =  yearSDF.format(cal.time)
//            val monthF=   monthSDF.format(cal.time)
//            val dayF = daySDF.format(cal.time)
//
//            //Convert Month and Time
//
//            val dateStr = dateSDF.parse(date)
//            cal.time = dateStr
//            val monthInt = cal.get(Calendar.MONTH)+1


            dbRef = FirebaseDatabase.getInstance().getReference( "events")
            dbRefArc = FirebaseDatabase.getInstance().getReference( "archives")

            val archive = ArchiveData(saveEventId, userName,eventTitle,location,date,time, description)

            popupMenu.inflate(R.menu.options_menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.archive-> {
                        /**set delete*/
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                //moved removed data to archives
                                dbRefArc.child(saveEventId).setValue(archive)
                                dbRef.child(currentItem.eventId.toString()).removeValue()
                                //remove in eventlist
                                eventList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c,"Post Deleted", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()

                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true

                    } //delete

                    R.id.edit->{
                        //find/get edit text
                        val v = LayoutInflater.from(c).inflate(R.layout.edit_event,null)
                        val etTitle = v.findViewById<EditText>(R.id.editEventName)
                        val etLocation = v.findViewById<EditText>(R.id.editLocation)
                        val etDescription = v.findViewById<EditText>(R.id.editDescription)
                        val etUserName = v.findViewById<TextView>(R.id.Username)
                        val etYear = v.findViewById<EditText>(R.id.editYear)
                        val etMonth = v.findViewById<EditText>(R.id.editMonth)
                        val etDay= v.findViewById<EditText>(R.id.editDay)
                        val etHour = v.findViewById<EditText>(R.id.editHour)
                        val etMinute= v.findViewById<EditText>(R.id.editMinute)
                        val etAMPM =v.findViewById<EditText>(R.id.editAMPM)

                        etUserName.setText(userName)
                        etTitle.setText(eventTitle)
                        etLocation.setText(location)
                        etYear.setText(year)
                        etMonth.setText(month)
                        etDay.setText(day)
                        etHour.setText(hour)
                        etMinute.setText(minute)
                        etAMPM.setText(amPm)
                        etDescription.setText(description)

                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Save"){
                                dialog,_->
                                //set the text- this is the current date and time from recycler view position
                                val yearStr= etYear.text.toString()
                                val monthStr= etMonth.text.toString()
                                val dayStr= etDay.text.toString()
                                val hourStr= etHour.text.toString()
                                val minStr= etMinute.text.toString()
                                val monthInt=monthStr.toIntOrNull()
                                val yearInt=yearStr.toIntOrNull()
                                val dayInt = dayStr.toIntOrNull()
                                val hourInt = hourStr.toIntOrNull()
                                val minInt= minStr.toIntOrNull()

                                pos.eventTitle= etTitle.text.toString()
                                pos.location= etLocation.text.toString()
                                pos.month= monthInt
                                pos.year=yearInt
                                pos.day=dayInt
                                pos.hour=hourInt
                                pos.minute=minInt
                                pos.amPm= etAMPM.text.toString()
                                pos.description= etDescription.text.toString()


                                val cal = Calendar.getInstance()
                                val month_date = SimpleDateFormat("MMMM")
                                val hour12Sdf =SimpleDateFormat("HH:mm a")
                                val hour24Sdf=SimpleDateFormat("HHmm")

                                cal[Calendar.MONTH] = (pos.month!!)-1
                                cal[Calendar.HOUR] = hourInt!!

                                val month_name = month_date.format(cal.time)


                                val stringMonth = String.format("%02d", pos.month)
                                val stringDay = String.format("%02d", pos.day)
                                val stringMinutes = String.format("%02d", pos.minute)
                                val stringHour = String.format("%02d", hourInt)

                                





                                pos.date = "${month_name} ${pos.day}, ${pos.year} "
                                val timeStr = "$hourInt:$stringMinutes $amPm"

                                pos.time=timeStr

                                val dateObj = hour12Sdf.parse(pos.time)
                                val newTime= hour24Sdf.format(dateObj)//output HHmm

                                val stringHour24 = String.format("%02d", newTime.toInt())

                                val newEventStamp=Long.parseLong("${pos.year}$stringMonth$stringDay$newTime")
                                pos.eventStamp = newEventStamp

                                //updateDataBase
                                val events = EventData(saveEventId,pos.userName,pos.eventTitle,pos.location,pos.date,pos.year,pos.month,
                                    pos.day,pos.time,pos.hour,pos.minute,currentItem.amPm ,pos.description,newEventStamp)

                                dbRef.child(currentItem.eventId.toString()).setValue(events)


                                notifyDataSetChanged()

                                Toast.makeText(c,"Event Updated", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()

                            }
                            .setNegativeButton("Cancel"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()



                    true
                    }


                    else -> true

                }// end of when()



            }//End of popupMenuClicklistener


            popupMenu.show()






        }




    }  //end of My viewHolder class







}

