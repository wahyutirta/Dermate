@file:Suppress("DEPRECATION")

package com.example.dermate.ui.result

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dermate.data.models.QuestionResultModel
import com.example.dermate.databinding.ActivityResultBinding
import com.example.dermate.viewmodel.ResultViewModel


class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var labels: List<String>
    private lateinit var image: Bitmap
    private lateinit var viewModel: ResultViewModel

    companion object {
        const val DATA = "data_result"

    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        labels = application.assets.open("labels_1.txt").bufferedReader().use { it.readText() }
            .split("\n")

        viewModel = ViewModelProvider(this)[ResultViewModel::class.java]


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
                    val h = webView.measuredHeight
                    //webView.minimumHeight = h
                    webView.visibility = View.VISIBLE

                }
            }

            /*
            webView.viewTreeObserver.addOnPreDrawListener(object : OnPreDrawListener{
                override fun onPreDraw(): Boolean {
                    val h = webView.measuredHeight
                    if (h != 0){
                        webView.viewTreeObserver.removeOnPreDrawListener(this)
                        webView.minimumHeight = h
                    }
                    return false
                }

            })

             */


            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.settings.allowContentAccess = true
            webView.settings.loadWithOverviewMode = true
            webView.settings.useWideViewPort = true
            webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        }


        val data = intent.getParcelableExtra<QuestionResultModel>(DATA)
        val uriImage = data?.uri
        val diseaseName = data?.diseaseName
        if (!diseaseName.isNullOrEmpty()) {
            viewModel.getDetailedData(diseaseName).observe(this, {
                setUi(diseaseName, uriImage, it.url)
            })
        }

    }


    private fun setUi(diseaseName: String?, uriImage: Uri?, url: String?) {
        image = MediaStore.Images.Media.getBitmap(this.contentResolver, uriImage)
        binding.apply {
            imageResultPreview.setImageBitmap(image)
            resultPredictionName1.text = diseaseName


            webView.loadUrl(url!!)
            webView.reload()
            webView.visibility = View.VISIBLE


        }

    }

}