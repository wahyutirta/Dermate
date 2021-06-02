@file:Suppress("UNCHECKED_CAST")

package com.example.dermate.data.models

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize


@Parcelize
data class ArticleModel(
    val url: List<String>?
) : Parcelable {
    companion object {
        fun DocumentSnapshot.toArticle(): ArticleModel? {
            return try {
                val name : List<String> =  get("article_list") as List<String>
                ArticleModel(name)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting data", e)
                null
            }
        }

        private const val TAG = "Article"
    }
}