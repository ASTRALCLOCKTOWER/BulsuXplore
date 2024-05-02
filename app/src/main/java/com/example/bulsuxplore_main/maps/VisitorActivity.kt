package com.example.bulsuxplore_main.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bulsuxplore_main.R
import com.example.bulsuxplore_main.databinding.ActivityMainBinding
import com.example.bulsuxplore_main.databinding.ActivityVisitorBinding

class VisitorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVisitorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisitorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(VisitorXploreFragment())  //Navigation view as default view!
        binding.bottomMenu.selectedItemId = R.id.xplore // Navigation menu icon as default
        binding.bottomMenu.background = null   //no background

//      Botoom menu add here//










        binding.bottomMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.xplore -> replaceFragment(VisitorXploreFragment())
                R.id.events -> replaceFragment(EventsFragment())
                R.id.profile-> replaceFragment(ProfileFragment())
            }













            //visitor
            true


        }}





    //only fragments will be replaced, while bottom navigation remains as it is
    private fun replaceFragment(fragment: androidx.fragment.app.Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }



}
