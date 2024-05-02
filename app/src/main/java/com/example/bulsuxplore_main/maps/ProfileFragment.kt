package com.example.bulsuxplore_main.maps

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.bulsuxplore_main.R
import com.example.bulsuxplore_main.users.ChangePasswordActivity
import com.example.bulsuxplore_main.users.LoginSignupActivity
import com.example.bulsuxplore_main.users.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ProfileFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var currentUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val currentUser = FirebaseAuth.getInstance().currentUser

        databaseReference = FirebaseDatabase.getInstance().getReference("users")

        // Display the user's name if available
        if (currentUser != null) {
            databaseReference.child(currentUser.uid).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(UserData::class.java)
                    val greetingTextView = view.findViewById<TextView>(R.id.textView2)
                    if (user != null && user.fullName?.isNotEmpty() == true) {
                        greetingTextView.text = "${user.fullName}"
                    } else {
                        greetingTextView.text = "Visitor"
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // check ano problem pag di nag work
                    Log.e("ProfileFragment", "Database error: ${databaseError.message}")
                }

            })
        }

        // to Display list of option
        val listview = view.findViewById<ListView>(R.id.settingslistView)
        val profile = arrayOf("Change Password", "Delete Account", "Sign out")
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(), android.R.layout.simple_list_item_1, profile
        )

        listview.adapter = arrayAdapter

        // click listener for list items
        listview.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = profile[position]
            // Handle click events for each item
            when (selectedItem) {
                "Change Password" -> {
                    startActivity(Intent(context, ChangePasswordActivity::class.java))

                }

                "Delete Account" -> {
                    showDeleteAccountConfirmationDialog()
                }

                "Sign out" -> {
                    showLogoutConfirmationDialog()
                }
            }
        }




        return view
    }


    private fun showDeleteAccountConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Account")
            .setMessage("Are you sure you want to delete your account?")
            .setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                deleteAccount()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteAccount() {
        currentUser.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Account deleted successfully
                    // Navigate to the sign-in screen or perform any other desired action
                    // You may also want to delete user data from the Realtime Database or Firestore
                } else {
                    // Failed to delete the account
                    // Display an error message or handle the failure as appropriate
                }
            }
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout") { _: DialogInterface, _: Int ->
                signOutUser()
                startActivity(Intent(context, LoginSignupActivity::class.java))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    private fun signOutUser() {
        FirebaseAuth.getInstance().signOut()
    }
}

