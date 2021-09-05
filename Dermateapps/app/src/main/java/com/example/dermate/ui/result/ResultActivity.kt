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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dermate.data.models.QuestionResultModel
import com.example.dermate.databinding.ActivityResultBinding
import com.example.dermate.ui.adapter.TreatmentRecyclerAdapter
import com.example.dermate.viewmodel.ResultViewModel


class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var labels: List<String>
    private lateinit var image: Bitmap
    private lateinit var viewModel: ResultViewModel
    private lateinit var treatmentsAdapter : TreatmentRecyclerAdapter

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


        val data = intent.getParcelableExtra<QuestionResultModel>(DATA)
        if (!data?.diseaseName.isNullOrEmpty()) {
            viewModel.getDetailedData(data?.diseaseName!!).observe(this, {
                setUi(data.diseaseName,  data.uri, it.url,it.solutions,it.treatmentSource)
            })
        }
    }
    private fun setUi(diseaseName: String?, uriImage: Uri?, url: String?, solutions : List<String>?, treatmentSource : String?) {
        image = MediaStore.Images.Media.getBitmap(this.contentResolver, uriImage)
        binding.apply {
            imageResultPreview.setImageBitmap(image)
            resultPredictionName1.text = diseaseName
            this.treatmentSource.text = treatmentSource

            treatmentsAdapter = TreatmentRecyclerAdapter(solutions!!)
            solutionRv.layoutManager = LinearLayoutManager(this@ResultActivity)
            solutionRv.adapter = treatmentsAdapter

            webView.apply {
                loadUrl(url!!)
                reload()
                visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}