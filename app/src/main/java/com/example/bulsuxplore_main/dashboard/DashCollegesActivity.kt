package com.example.bulsuxplore_main.dashboard

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.bulsuxplore_main.databinding.ActivityDashcollegesBinding


class DashCollegesActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDashcollegesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashcollegesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myWebView: WebView = binding.collegeswebview
        myWebView.loadUrl("https://www.bulsu.edu.ph/academics/")
        myWebView.settings.javaScriptEnabled = true
//

        // Set a WebViewClient to handle URL loading within the WebView
        myWebView.webViewClient = MyWebViewClient()
    }

    // WebViewClient to handle URL loading within the WebView
    private class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            url?.let {
                view?.loadUrl(it)
                return true
            }
            return false
        }
    }
}