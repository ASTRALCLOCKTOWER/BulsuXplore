package com.example.bulsuxplore_main.users

import com.example.bulsuxplore_main.maps.MainActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bulsuxplore_main.databinding.ActivityLogInBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

//USER LOGIN using bulsu.edu.ph //
class LogInActivity : AppCompatActivity() {

private lateinit var binding: ActivityLogInBinding
private lateinit var firebaseDatabase: FirebaseDatabase
private lateinit var databaseReference: DatabaseReference
private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance() // Initialize Firebase realtime database
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference= firebaseDatabase.reference.child("users")


        binding.loginButton.setOnClickListener {
// no need full name

            val loginEmail = binding.loginEmail.text.toString()
            val loginPassword = binding.loginPassword.text.toString()

            if (loginEmail.isNotEmpty() && loginPassword.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(loginEmail, loginPassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            if (user != null) {
                                if (user.isEmailVerified) {
                                    Toast.makeText(
                                        this@LogInActivity,
                                        "Login Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this@LogInActivity, MainActivity::class.java))
                                    finish()
                                } else {
//                                    pansamantalang bypass; remove after development
                                    startActivity(Intent(this@LogInActivity, MainActivity::class.java))
                                    // Email is not vdierified
                                    Toast.makeText(
                                        this@LogInActivity,
                                        "bypass.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                this@LogInActivity,
                                "Invalid Credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    this@LogInActivity,
                    "All fields are mandatory",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        binding.signupRedirect.setOnClickListener {
            startActivity(Intent(this@LogInActivity, SignupActivity::class.java))
            finish()
        }

    }
    override fun onBackPressed() {
        startActivity(Intent(this@LogInActivity, LoginSignupActivity::class.java))
        super.onBackPressed()
    }
}



