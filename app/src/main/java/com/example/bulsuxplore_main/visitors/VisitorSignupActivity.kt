package com.example.bulsuxplore_main.visitors

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bulsuxplore_main.databinding.ActivitySignupVisitorBinding
import com.example.bulsuxplore_main.maps.MainActivity

import com.example.bulsuxplore_main.users.LogInActivity

class VisitorSignupActivity : AppCompatActivity() {

            private lateinit var binding: ActivitySignupVisitorBinding

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)


                binding = ActivitySignupVisitorBinding.inflate(layoutInflater)
                setContentView(binding.root)



                binding.visitorverifyRedirect.setOnClickListener {
                    val visitorFullName = binding.visitorFullName.text.toString()
                    val visitorPhoneNumber = binding.visitorPhoneNumber.text.toString()
                    startActivity(Intent(this@VisitorSignupActivity, MainActivity::class.java))
                    finish()

                }
            }}






