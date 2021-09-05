package com.example.dermate.ui.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.dermate.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    companion object {
        const val ARTICLE_TITLE = "title"
        const val URL = "url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val title = intent.getStringExtra(ARTICLE_TITLE)
        val url: String? = intent.getStringExtra(URL)
        //supportActionBar?.title = title
        setupWebView()

        binding.webView.loadUrl(url!!)

    }

    private fun setupWebView() {
        binding.apply {

            webView.visibility = View.GONE
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: String
                ): Boolean {
                    view?.loadUrl(request)
                    return false
                }
            }

            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    webView.visibility = View.VISIBLE

                }
            }
            webView.settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                allowContentAccess = true
                loadWithOverviewMode = true
                useWideViewPort = true
                cacheMode = WebSettings.LOAD_DEFAULT
            }
        }
    }
}