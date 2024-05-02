package com.example.bulsuxplore_main.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.bulsuxplore_main.R
import com.example.bulsuxplore_main.databinding.FragmentXploreBinding

class XploreFragment : Fragment() {

    private lateinit var binding: FragmentXploreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//         Inflate the layout for this fragment
        binding = FragmentXploreBinding.inflate(inflater, container, false)
//        mapView = binding.mapView

        val myWebView: WebView? = binding.root.findViewById(R.id.webView)
        myWebView?.loadUrl("https://komichiwa.github.io/#19/14.85756/120.81565")
        myWebView?.settings?.javaScriptEnabled = true

        // Set a WebViewClient to handle URL loading within the WebView
        myWebView?.webViewClient = MyWebViewClient()

        return binding.root
    }
    // Custom WebViewClient to handle URL loading within the WebView
    private class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            url?.let {
                view?.loadUrl(it) // Load the URL within the WebView itself
                return true // Indicate that the WebView should handle the URL loading
            }
            return false // Return false to indicate that the WebView should not handle the URL loading
        }
    }}




