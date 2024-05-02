package com.example.bulsuxplore_main.maps.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bulsuxplore_main.R
import com.example.bulsuxplore_main.maps.data.Orgs
import com.google.android.material.imageview.ShapeableImageView

data class OrgListAdapter(private val orgsList : ArrayList<Orgs>):
    RecyclerView.Adapter<OrgListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_org, parent, false)
        //i added new argument for eventslistener
        return MyViewHolder(itemView)


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = orgsList[position]
//    changeable to variable name
        holder.orgImage.setImageResource(currentItem.orgImage)
        holder.orgGroup.text = currentItem.orgGroup
        holder.orgDesc.text = currentItem.orgDesc
        holder.orgList.text = currentItem.orgList
    }

    override fun getItemCount(): Int {
        return orgsList.size // how many item are there in the cycler view

    }

    //i added listener events sa argument
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        //   1st:  declare all the elements here
        val orgImage: ShapeableImageView = itemView.findViewById(R.id.org_image)
        val orgGroup: TextView = itemView.findViewById(R.id.org_group)
        val orgDesc: TextView = itemView.findViewById(R.id.org_desc)
        val orgList: TextView = itemView.findViewById(R.id.org_list)


        }


    }


