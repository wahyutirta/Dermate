package com.example.dermate.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dermate.R
import com.example.dermate.databinding.TreatmentItemBinding


class TreatmentRecyclerAdapter(private val treatmentList: List<String>) :
    RecyclerView.Adapter<TreatmentRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = TreatmentItemBinding.bind(view)

        internal fun bind(solution: String) {
            binding.solutionItem.text = solution
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TreatmentRecyclerAdapter.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.treatment_item, parent, false)
        return ViewHolder(view)
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: TreatmentRecyclerAdapter.ViewHolder, position: Int) {
        holder.bind(treatmentList[position])
    }

    override fun getItemCount(): Int = treatmentList.size
}