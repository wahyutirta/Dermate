@file:Suppress("DEPRECATION")

package com.example.dermate.ui.result

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.dermate.data.models.QuestionResultModel
import com.example.dermate.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var labels: List<String>
    private lateinit var image: Bitmap
    private lateinit var labelIndex: List<Int>
    private lateinit var uriImage: Uri

    companion object {
        const val DATA = "data_result"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        labels = application.assets.open("labels_1.txt").bufferedReader().use { it.readText() }
            .split("\n")

        val data = intent.getParcelableExtra<QuestionResultModel>(DATA)
        val uriImage = data?.uri
        val diseaseName = data?.diseaseName
        setUi(diseaseName,uriImage)

    }

    private fun setUi(diseaseName: String?, uriImage : Uri?) {

        image = MediaStore.Images.Media.getBitmap(this.contentResolver, uriImage)
        binding.apply {
            imageResultPreview.setImageBitmap(image)
            resultPredictionName1.text = diseaseName

        }

    }
}