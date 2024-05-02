package com.example.bulsuxplore_main.users

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bulsuxplore_main.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var currentPass: EditText
    private lateinit var newPass: EditText
    private lateinit var confirmPass: EditText
    private lateinit var submitBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        currentPass = findViewById(R.id.currentPass)
        newPass = findViewById(R.id.newPass)
        confirmPass = findViewById(R.id.confirmPass)
        submitBtn = findViewById(R.id.submitBtn)

        submitBtn.setOnClickListener {
            val currentPassword = currentPass.text.toString()
            val newPassword = newPass.text.toString()
            val confirmNewPassword = confirmPass.text.toString()

            if (newPassword != confirmNewPassword) {
                // Show error message if passwords do not match
                Toast.makeText(
                    this,
                    "New password and confirmation password do not match",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Re-authenticate the user
            val user = FirebaseAuth.getInstance().currentUser
            val credential = EmailAuthProvider.getCredential(user!!.email!!, currentPassword)
            user.reauthenticate(credential)
                .addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        // If re-authentication is successful, update the password
                        user.updatePassword(newPassword)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "Password updated successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Failed to update password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            this,
                            "Authentication failed. Please check your current password.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}