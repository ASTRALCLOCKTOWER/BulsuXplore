package com.example.bulsuxplore_main.users

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.text.HtmlCompat
import androidx.viewpager.widget.ViewPager
import com.example.bulsuxplore_main.R
import com.example.bulsuxplore_main.maps.MainActivity
import com.example.bulsuxplore_main.users.SliderScreen

class SliderNavigationActivity : AppCompatActivity() {
    private lateinit var slideViewPager: ViewPager
    private lateinit var dotIndicator: LinearLayout
    private lateinit var backButton: Button
    private lateinit var nextButton: Button
    private lateinit var skipButton: Button
    private lateinit var dots: Array<TextView>
    private lateinit var viewPagerAdapter: SliderScreen

    private val viewPagerListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            // Empty implementation
        }

        // visible back button for 2nd page and up
        override fun onPageSelected(position: Int) {
            setDotIndicator(position)
            backButton.visibility = if (position > 0) View.VISIBLE else View.INVISIBLE
            nextButton.text = if (position == 2) "Start" else "Next"
        }

        override fun onPageScrollStateChanged(state: Int) {
            // Empty implementation
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()    // installing splash screen
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider_navigation)

        backButton = findViewById(R.id.back_button)
        nextButton = findViewById(R.id.next_button)
        skipButton = findViewById(R.id.skip_button)

        backButton.setOnClickListener {
            if (getItem(0) > 0) {
                slideViewPager.setCurrentItem(getItem(-1), true)
            }
        }

        nextButton.setOnClickListener {
            if (getItem(0) < 2) {
                slideViewPager.setCurrentItem(getItem(1), true)
            } else {
                val i = Intent(this@SliderNavigationActivity, LoginSignupActivity::class.java)
                startActivity(i)
                finish()
            }
        }

        skipButton.setOnClickListener {
            val i = Intent(this@SliderNavigationActivity, LoginSignupActivity::class.java)
            startActivity(i)
            finish()
        }

        slideViewPager = findViewById(R.id.slide_view)
        dotIndicator = findViewById(R.id.dotIndicator)

        viewPagerAdapter = SliderScreen(this)
        slideViewPager.adapter = viewPagerAdapter
        setDotIndicator(0)
        slideViewPager.addOnPageChangeListener(viewPagerListener)
    }


    // dot indicator switching pages
    private fun setDotIndicator(position: Int) {
        dots = Array(3) { TextView(this) }
        dotIndicator.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i].text = HtmlCompat.fromHtml("&#8226", HtmlCompat.FROM_HTML_MODE_LEGACY)
            dots[i].textSize = 35f
            dots[i].setTextColor(ContextCompat.getColor(this, R.color.grey))
            dotIndicator.addView(dots[i])
        }

        dots[position].setTextColor(ContextCompat.getColor(this, R.color.yellow))
    }

    private fun getItem(i: Int): Int {
        return slideViewPager.currentItem + i
    }
}