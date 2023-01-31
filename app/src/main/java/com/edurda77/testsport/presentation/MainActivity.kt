package com.edurda77.testsport.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebViewClient
import com.edurda77.testsport.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("SetJavaScriptEnabled")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val webView = binding.webView
        webView.webViewClient = WebViewClient()
        val webSettings = webView.settings
        //webSettings.javaScriptEnabled = true
        webView.loadUrl("https://google.com/")
        //webView.loadUrl("https://ya.ru/")
        //initWebView(savedInstanceState, url="https://ya.ru/")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(savedInstanceState: Bundle?, url:String) {
        //val webView = binding.webView
        //webView.webViewClient = WebViewClient()
        //val webSettings = webView.settings
        //webSettings.javaScriptEnabled = true
        /*if (savedInstanceState != null)
            webView.restoreState(savedInstanceState)
        else*/
          //  webView.loadUrl("https://ya.ru/")
        /*webView.settings.domStorageEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        webSettings.javaScriptEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true
        webSettings.setSupportZoom(false)
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true*/

    }
}