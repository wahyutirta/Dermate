package com.example.dermate.ui.question

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.dermate.R
import com.example.dermate.data.models.QuestionDataModel
import com.example.dermate.databinding.ActivityAskingQuestionsBinding

class AskingQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAskingQuestionsBinding

    companion object {
        const val QUESTION_DATA = "question data"
        const val EXTRA_VALUE = "extra value"
    }

    private var yesOpt = 0
    private var resultCode =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAskingQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<QuestionDataModel>(QUESTION_DATA)
        resultCode = data?.resultCode!!
        bindQuestions(data.url)
        binding.submitAnswer.setOnClickListener(this)
    }

    private fun bindQuestions(data: List<String>?) {
        binding.questionText1.text = data?.get(0)
        binding.questionText2.text = data?.get(1)
        binding.questionText3.text = data?.get(2)
        binding.questionText4.text = data?.get(3)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.submit_answer->{
                var data1 = 0
                var data2 = 0
                var data3 = 0
                var data4 = 0
                if(binding.rgQuest1.checkedRadioButtonId>0){
                    when(binding.rgQuest1.checkedRadioButtonId){
                        R.id.rb_yes1-> data1 = 1
                    }
                }
                if(binding.rgQuest2.checkedRadioButtonId>0){
                    when(binding.rgQuest2.checkedRadioButtonId){
                        R.id.rb_yes2-> data2 = 1
                    }
                }
                if(binding.rgQuest3.checkedRadioButtonId>0){
                    when(binding.rgQuest3.checkedRadioButtonId){
                        R.id.rb_yes3-> data3 = 1
                    }
                }
                if(binding.rgQuest4.checkedRadioButtonId>0){
                    when(binding.rgQuest4.checkedRadioButtonId){
                        R.id.rb_yes4-> data4 = 1
                    }
                }

                yesOpt = data1+data2+data3+data4

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_VALUE,yesOpt)
                setResult(resultCode,resultIntent)
                finish()
            }
        }
    }
}