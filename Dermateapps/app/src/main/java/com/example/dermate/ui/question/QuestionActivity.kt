@file:Suppress("DEPRECATION")

package com.example.dermate.ui.question

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.dermate.data.models.QuestionDataModel
import com.example.dermate.data.models.QuestionResultModel
import com.example.dermate.data.models.ResultModel
import com.example.dermate.databinding.ActivityQuestionBinding
import com.example.dermate.ui.result.ResultActivity
import com.example.dermate.viewmodel.QuestionViewModel

class QuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionBinding
    private lateinit var labels: List<String>
    private lateinit var questionViewModel: QuestionViewModel

    private lateinit var imageUri: Uri
    private var resultLabel = ""
    private var label1 = ""
    private var label2 = ""
    private var label3 = ""
    private var totalTrueQuestion1 = 0
    private var totalTrueQuestion2 = 0
    private var totalTrueQuestion3 = 0

    private lateinit var labelIndex: List<Int>

    companion object {
        const val DATA = "data"
        const val QUEST1 = 1001
        const val QUEST2 = 1002
        const val QUEST3 = 1003

        const val RESULT_CODE1 = 2001
        const val RESULT_CODE2 = 2002
        const val RESULT_CODE3 = 2003
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        labels = application.assets.open("labels_1.txt").bufferedReader().use { it.readText() }
            .split("\n")

        val data = intent.getParcelableExtra<ResultModel>(DATA)
        imageUri = data?.imageUri!!

        questionViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]

        labelIndex = data.id ?: listOf()

        label1 = labels[labelIndex[0]].replace("\r", "")
        label2 = labels[labelIndex[1]].replace("\r", "")
        label3 = labels[labelIndex[2]].replace("\r", "")

        observeQuestions1(label1)
        observeQuestions2(label2)
        observeQuestions3(label3)


    }

    private fun observeQuestions1(label: String) {
        questionViewModel.getSpecifiedData1(label).observe(this, { data ->
            data.questions?.let {
                val intent = Intent(this, AskingQuestionsActivity::class.java)
                intent.putExtra(
                    AskingQuestionsActivity.QUESTION_DATA,
                    QuestionDataModel(it, RESULT_CODE1)
                )
                startActivityForResult(intent, QUEST1)
            }
        })

    }

    private fun observeQuestions2(label: String) {
        questionViewModel.getSpecifiedData2(label).observe(this, { data ->
            data.questions?.let {
                val intent = Intent(this, AskingQuestionsActivity::class.java)
                intent.putExtra(
                    AskingQuestionsActivity.QUESTION_DATA,
                    QuestionDataModel(it, RESULT_CODE2)
                )
                startActivityForResult(intent, QUEST2)
            }
        })

    }

    private fun observeQuestions3(label: String) {
        questionViewModel.getSpecifiedData3(label).observe(this, { data ->
            data.questions?.let {
                val intent = Intent(this, AskingQuestionsActivity::class.java)
                intent.putExtra(
                    AskingQuestionsActivity.QUESTION_DATA,
                    QuestionDataModel(it, RESULT_CODE3)
                )
                startActivityForResult(intent, QUEST3)
            }
        })
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {//iterate from the last
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == QUEST1) {
            if (resultCode == RESULT_CODE1) {
                totalTrueQuestion1 = data?.getIntExtra(AskingQuestionsActivity.EXTRA_VALUE, 0)!!

                showResult()
            }
        } else if (requestCode == QUEST2) {
            if (resultCode == RESULT_CODE2) {
                totalTrueQuestion2 = data?.getIntExtra(AskingQuestionsActivity.EXTRA_VALUE, 0)!!
            }
        } else if (requestCode == QUEST3) {
            if (resultCode == RESULT_CODE3) {
                totalTrueQuestion3 = data?.getIntExtra(AskingQuestionsActivity.EXTRA_VALUE, 0)!!

            }
        }
    }

    private fun showResult() {
        if (totalTrueQuestion1 > totalTrueQuestion2 && totalTrueQuestion1 > totalTrueQuestion3) {
            resultLabel = label1
        } else if (totalTrueQuestion2 > totalTrueQuestion1 && totalTrueQuestion2 > totalTrueQuestion3) {
            resultLabel = label2
        } else if (totalTrueQuestion3 > totalTrueQuestion1 && totalTrueQuestion3 > totalTrueQuestion2) {
            resultLabel = label3
        }
        val intent = Intent(this@QuestionActivity, ResultActivity::class.java)
        intent.putExtra(ResultActivity.DATA, QuestionResultModel(imageUri, resultLabel))
        startActivity(intent)
        finish()
    }


}