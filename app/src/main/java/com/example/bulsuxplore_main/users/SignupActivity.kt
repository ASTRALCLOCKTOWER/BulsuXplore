package com.example.bulsuxplore_main.users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bulsuxplore_main.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    //declare firebase database references
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference // requires to create a connection to google database
    private lateinit var firebaseAuth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        // overall = to connect/bind XML design with Kotlin Code
        //binding = to connect to UI elements to kotlin code
        //ActivitySignupBinding = derived from the name of our xml file "activity_signup.xml"
        //.inflate = to convert
        //(layoutInflater) = to convert XML layout file to be viewable

        setContentView(binding.root)
        //Overall line = to display UI design to the user screen
        //setContentView() = to display the UI design in XML file
        // binding.root = top level view ng xml file which is yung constraint layout

        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        //Overall = To initialize the database
        //firebaseDatabase = reference(pointer/identifier) indirect / not actual data but a way to access
        //FirebaseDatabase = a static class
        //getInstance() = static method ng FirebaseDatabase to access and manage database


        databaseReference = firebaseDatabase.reference.child("users")
        //overall line = databaseReference is a reference when need to access user
        //databaseReference = holds reference to the "users"
        //firebaseDatabase = reference to database
        //.reference = root reference of the database / top level access
        //child = navigate to specific location


        binding.signupButton.setOnClickListener {
            val signupFullName = binding.signupFullName.text.toString()
            val signupEmail = binding.signupEmail.text.toString()
            val signupPhoneNumber = binding.signupPhoneNumber.text.toString()
            val signupPassword = binding.signupPassword.text.toString()

            if (signupEmail.endsWith("@bulsu.edu.ph") || signupEmail.startsWith("org.")) {
                if (signupFullName.isNotEmpty() && signupEmail.isNotEmpty() && signupPhoneNumber.isNotEmpty() && signupPassword.isNotEmpty()) {
                    // Create a new user in Firebase Authentication
                    firebaseAuth.createUserWithEmailAndPassword(signupEmail, signupPassword)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = firebaseAuth.currentUser

                                if (user != null) {
                                    // Send email verification
                                    user.sendEmailVerification()
                                        .addOnCompleteListener { verificationTask ->
                                            if (verificationTask.isSuccessful) {
                                                Toast.makeText(
                                                    this@SignupActivity,
                                                    "Verification email sent. Please verify your email.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    this@SignupActivity,
                                                    "Verification email sending failed: ${verificationTask.exception?.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }

                                    // Store user profile data in the Realtime Database
                                    storeUserProfile(user.uid, signupFullName, signupEmail, signupPhoneNumber)

                                    startActivity(Intent(this@SignupActivity, LogInActivity::class.java))
                                    finish()
                                }
                            } else {
                                Toast.makeText(
                                    this@SignupActivity,
                                    "Sign-up failed: ${task.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        this@SignupActivity,
                        "All fields are mandatory",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this@SignupActivity,
                    "Sign-up with '@bulsu.edu.ph' email addresses only",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        binding.loginRedirect.setOnClickListener {
            startActivity(Intent(this@SignupActivity, LogInActivity::class.java))
            finish()
        }
    }

    private fun storeUserProfile(uid: String, fullName: String, email: String, phoneNumber: String) {
        val storeUserProfile = UserData(uid,fullName, email, phoneNumber)

        // Reference to the "users" node in the Realtime Database
        val userReference = databaseReference.child(uid)

        // Set the user data under the user's UID
        userReference.setValue(storeUserProfile)
    }
    override fun onBackPressed() {
        startActivity(Intent(this@SignupActivity, LoginSignupActivity::class.java))
        super.onBackPressed()
    }
}