package com.example.bulsuxplore_main.maps


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.bulsuxplore_main.R
import com.example.bulsuxplore_main.databinding.FragmentVisitorXploreBinding

class VisitorXploreFragment : Fragment() {

    private lateinit var binding: FragmentVisitorXploreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVisitorXploreBinding.inflate(inflater, container, false)

        val myWebView: WebView? = binding.root.findViewById(R.id.webView1)
        myWebView?.loadUrl("https://komichiwa.github.io/#19.5/14.85762/120.81456")
        myWebView?.settings?.javaScriptEnabled = true

        // Set a WebViewClient to handle URL loading within the WebView
        myWebView?.webViewClient = MyWebViewClient()
        myWebView?.getSettings()?.setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= 19) {
            myWebView?.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            myWebView?.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

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