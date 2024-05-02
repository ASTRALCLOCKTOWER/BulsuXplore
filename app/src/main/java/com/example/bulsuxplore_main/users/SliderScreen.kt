package com.example.bulsuxplore_main.users

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.bulsuxplore_main.R

class SliderScreen(private val context: Context) : PagerAdapter() {

    private val sliderAllImage = intArrayOf(R.drawable.main_icon_logo, R.drawable.xplore_logo, R.drawable.events_logo)
    private val sliderAllTitle = intArrayOf(R.string.slider1Name,R.string.slider2Name,R.string.slider3Name)
    private val sliderAllDesc = intArrayOf(R.string.slider1Desc,R.string.slider2Desc,R.string.slider3Desc)


    override fun getCount(): Int {
        return sliderAllImage.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.activity_sliderscreen, container, false)

        val sliderImage: ImageView = view.findViewById(R.id.slider_image)
        val sliderTitle: TextView = view.findViewById(R.id.slider_title)
        val sliderDesc: TextView = view.findViewById(R.id.slider_descp)

        sliderImage.setImageResource(sliderAllImage[position])
        sliderTitle.setText(context.getString(sliderAllTitle[position]))
        sliderDesc.setText(context.getString(sliderAllDesc[position]))

        container.addView(view)
        return view
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}