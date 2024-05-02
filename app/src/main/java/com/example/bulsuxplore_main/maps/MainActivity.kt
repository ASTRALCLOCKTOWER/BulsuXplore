package com.example.bulsuxplore_main.maps


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bulsuxplore_main.R

import com.example.bulsuxplore_main.databinding.ActivityMainBinding
import com.example.bulsuxplore_main.users.LogInActivity
import com.example.bulsuxplore_main.users.LoginSignupActivity
import com.google.firebase.auth.FirebaseAuth


// we allow user to choose activities from bottom menus //
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        setupBottomNavigation()
        checkAuthenticationState()

        replaceFragment(HomeFragment())
        binding.bottomMenu.background = null
        //no background


//      click events for navigation menus
        binding.bottomMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.xplore -> replaceFragment(XploreFragment())
                R.id.events -> {
                    if (firebaseAuth.currentUser != null) {
                        replaceFragment(EventsFragment())
                    } else {

                    }
                }

                R.id.profile -> {
                    if (firebaseAuth.currentUser != null) {
                        replaceFragment(ProfileFragment())
                    } else {

                    }
                }

                R.id.login -> {
                    startActivity(Intent(this, LoginSignupActivity::class.java))
                }
            }
            true
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomMenu.selectedItemId = R.id.home
        binding.bottomMenu.background = null


    }


    //only fragments will be replaced, while bottom navigation remains as it is
    private fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }


    private fun checkAuthenticationState() {
        firebaseAuth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            binding.bottomMenu.menu.findItem(R.id.events).isVisible = user != null
            binding.bottomMenu.menu.findItem(R.id.profile).isVisible = user != null
            binding.bottomMenu.menu.findItem(R.id.login).isVisible = user == null

                }
            }
        }





