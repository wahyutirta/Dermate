package com.example.dermate.ui.adapter.question

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dermate.R
import com.example.dermate.databinding.QuestionItemBinding

class QuestionAdapter(private val diseaseData: List<String>) :
    RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = QuestionItemBinding.bind(view)

        internal fun bind(data: String) {
            binding.questionText.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(diseaseData[position])
    }

    override fun getItemCount(): Int = diseaseData.size

}