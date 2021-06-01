package com.example.dermate.ui.adapter

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dermate.R
import com.example.dermate.databinding.ArticleItemBinding
import io.github.ponnamkarthik.richlinkpreview.MetaData
import io.github.ponnamkarthik.richlinkpreview.ResponseListener
import io.github.ponnamkarthik.richlinkpreview.RichPreview


class ArticleListViewAdapter(private val articleList: List<String>) :
    RecyclerView.Adapter<ArticleListViewAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ArticleItemBinding.bind(view)
        private lateinit var richPreview: RichPreview
        internal fun bind(url: String) {

            extractUrl(url)

            itemView.setOnClickListener {
                try {
                    val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    itemView.context.startActivity(myIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        itemView.context, "No application can handle this request.", Toast.LENGTH_LONG
                    ).show()
                    e.printStackTrace()
                }
            }
        }

        private fun extractUrl(url: String) {
            var data: MetaData

            richPreview = RichPreview(object : ResponseListener {
                override fun onData(metaData: MetaData) {
                    data = metaData

                    val title = data.title.split(" ")
                    val trimTitle: String = if (title.size > 5) {
                        "${title.take(5).joinToString(" ")}..."
                    } else {
                        title.joinToString(" ")
                    }
                    binding.apply {
                        articleProvider.text = "Article by ${data.sitename}"
                        urlTitle.text = trimTitle
                        Glide.with(itemView.context.applicationContext).load(data.imageurl).into(imageThumbnail)
                    }
                }

                override fun onError(e: Exception) {

                }
            })
            richPreview.getPreview(url)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleListViewAdapter.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleListViewAdapter.ViewHolder, position: Int) {

        holder.bind(articleList[position])

    }

    override fun getItemCount(): Int = articleList.size
}