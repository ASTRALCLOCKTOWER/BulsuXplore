package com.example.bulsuxplore_main.dashboard


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bulsuxplore_main.databinding.ActivityDashfaqsBinding


class DashFaqsActivity : AppCompatActivity()  {



    private lateinit var binding: ActivityDashfaqsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashfaqsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}