package com.example.bulsuxplore_main.users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bulsuxplore_main.databinding.ActivityLoginSignupBinding
import com.example.bulsuxplore_main.maps.MainActivity
import com.example.bulsuxplore_main.maps.VisitorActivity
import com.example.bulsuxplore_main.maps.VisitorXploreFragment
import com.example.bulsuxplore_main.visitors.VisitorSignupActivity

// this is the initial page where we let user to choose: LOGIN, SIGNUP, or VISITOR ACCESS
class LoginSignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSignupBinding


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityLoginSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


       binding.loginRedirect.setOnClickListener {
            startActivity(Intent(this@LoginSignupActivity, LogInActivity::class.java))
            finish()
        }

        binding.signupRedirect.setOnClickListener {
            startActivity(Intent(this@LoginSignupActivity, SignupActivity::class.java))
            finish()
        }
        binding.visitorsignupRedirect.setOnClickListener {
            startActivity(Intent(this@LoginSignupActivity, MainActivity::class.java))
            finish()
        }



    }
}