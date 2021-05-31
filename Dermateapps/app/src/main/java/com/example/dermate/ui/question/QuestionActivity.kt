package com.example.dermate.ui.question

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dermate.data.models.ResultModel
import com.example.dermate.databinding.ActivityQuestionBinding
import com.example.dermate.ui.adapter.question.QuestionAdapter
import com.example.dermate.viewmodel.QuestionViewModel

class QuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionBinding
    private lateinit var labels: List<String>
    private lateinit var questionViewModel: QuestionViewModel
    private lateinit var question1Adapter: QuestionAdapter
    private lateinit var question2Adapter: QuestionAdapter
    private lateinit var question3Adapter: QuestionAdapter
    var totalTrueQuestion1 = 0
    var totalTrueQuestion2 = 0
    var totalTrueQuestion3 = 0


    private lateinit var labelIndex: List<Int>

    companion object {
        const val DATA = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        labels = application.assets.open("labels_1.txt").bufferedReader().use { it.readText() }
            .split("\n")

        val data = intent.getParcelableExtra<ResultModel>(DATA)

        questionViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]

        labelIndex = data?.id ?: listOf()

        val label1 = labels[labelIndex[0]].replace("\r", "")
        val label2 = labels[labelIndex[1]].replace("\r", "")
        val label3 = labels[labelIndex[2]].replace("\r", "")

        observeQuestions1(label1)
        observeQuestions2(label2)
        observeQuestions3(label3)

    }

    private fun observeQuestions1(label: String) {
        questionViewModel.getSpecifiedData1(label).observe(this, { data ->
            data.questions?.let {
                setRvQuest1(it)
            }
        })
    }

    private fun observeQuestions2(label: String) {
        questionViewModel.getSpecifiedData2(label).observe(this, { data ->
            data.questions?.let {
                setRvQuest2(it)
            }
        })
    }

    private fun observeQuestions3(label: String) {
        questionViewModel.getSpecifiedData3(label).observe(this, { data ->
            data.questions?.let {
                setRvQuest3(it)
            }
        })
    }

    private fun setRvQuest1(quest: List<String>) {
        binding.apply {
            q1.layoutManager = LinearLayoutManager(this@QuestionActivity)
            question1Adapter = QuestionAdapter(quest)
            q1.adapter = question1Adapter
        }
    }

    private fun setRvQuest2(quest: List<String>) {
        binding.apply {
            q2.layoutManager = LinearLayoutManager(this@QuestionActivity)
            question2Adapter = QuestionAdapter(quest)
            q2.adapter = question2Adapter
        }
    }

    private fun setRvQuest3(quest: List<String>) {
        binding.apply {
            q3.layoutManager = LinearLayoutManager(this@QuestionActivity)
            question3Adapter = QuestionAdapter(quest)
            q3.adapter = question3Adapter
        }
    }


}