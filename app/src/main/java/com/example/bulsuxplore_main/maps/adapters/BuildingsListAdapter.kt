package com.example.bulsuxplore_main.maps.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bulsuxplore_main.R
import com.example.bulsuxplore_main.maps.data.Buildings
import com.google.android.material.imageview.ShapeableImageView


data class BuildingsListAdapter(private val buildingsList : ArrayList<Buildings>  ) :
    RecyclerView.Adapter<BuildingsListAdapter.MyViewHolder>() {

    private lateinit var tapListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){
        tapListener = listener
    }
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MyViewHolder {

    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_buildings, parent, false)
    //i added new argument for eventslistener
    return MyViewHolder(itemView,tapListener)


}

override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val currentItem = buildingsList[position]
//    changeable to variable name
    holder.buildingImage.setImageResource(currentItem.buildingImage)
    holder.buildingName.text = currentItem.buildingName
    holder.buildingCollege.text = currentItem.buildingCollege
}

override fun getItemCount(): Int {
   return buildingsList.size // how many item are there in the cycler view

}

    //i added listener events sa argument
class MyViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {

    //   1st:  declare all the elements here
    val buildingImage: ShapeableImageView = itemView.findViewById(R.id.building_image)
    val buildingName: TextView = itemView.findViewById(R.id.building_name)
    val buildingCollege: TextView = itemView.findViewById(R.id.building_college)

        init {
            itemView.setOnClickListener(){
                listener.onItemClick(adapterPosition)

            }
        }


}
}
