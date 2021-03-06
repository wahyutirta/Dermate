package com.example.dermate.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dermate.R
import com.example.dermate.databinding.ArticleItemBinding
import com.example.dermate.ui.webview.WebViewActivity
import io.github.ponnamkarthik.richlinkpreview.MetaData
import io.github.ponnamkarthik.richlinkpreview.ResponseListener
import io.github.ponnamkarthik.richlinkpreview.RichPreview


class ArticleRecyclerAdapter(private val articleList: List<String>) :
    RecyclerView.Adapter<ArticleRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ArticleItemBinding.bind(view)
        private lateinit var richPreview: RichPreview
        @ExperimentalStdlibApi
        internal fun bind(url: String) {

            extractUrl(url)

            itemView.setOnClickListener {
                val goView = Intent(itemView.context,WebViewActivity::class.java)
                goView.putExtra(WebViewActivity.URL,url)
                itemView.context.startActivity(goView)
                /*
                try {
                    val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    itemView.context.startActivity(myIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        itemView.context, "No application can handle this request.", Toast.LENGTH_LONG
                    ).show()
                    e.printStackTrace()
                }

                 */
            }
        }

        @ExperimentalStdlibApi
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
                        val halodoc = "HALODOC"
                        val alodokter = "ALODOKTER"
                        val articleProviderText ="Article by ${data.sitename}"
                        when {
                            data.sitename.equals(halodoc,true) -> {
                                articleProvider.text = articleProviderText
                                articleProvider.setTextColor(itemView.context.getColor(R.color.red_pastel))
                            }
                            data.sitename.equals(alodokter,true) -> {
                                articleProvider.text = articleProviderText
                                articleProvider.setTextColor(itemView.context.getColor(R.color.blue_pastel))
                            }
                            else -> {
                                articleProvider.text = articleProviderText
                                articleProvider.setTextColor(itemView.context.getColor(R.color.theme_color))
                            }
                        }

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
    ): ArticleRecyclerAdapter.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return ViewHolder(view)
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: ArticleRecyclerAdapter.ViewHolder, position: Int) {
        holder.bind(articleList[position])
    }

    override fun getItemCount(): Int = articleList.size
}