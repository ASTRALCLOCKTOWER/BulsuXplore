package com.example.bulsuxplore_main.dashboard

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.bulsuxplore_main.databinding.ActivityDashportalBinding


class DashPortalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashportalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashportalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myWebView: WebView = binding.portalview
        myWebView.loadUrl("https://bulsu.priisms.online/auth/login")
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
