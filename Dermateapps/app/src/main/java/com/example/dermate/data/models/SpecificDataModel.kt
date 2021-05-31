package com.example.dermate.data.models

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpecificDataModel (
    val url : String?,
    val questions : List<String>?
) : Parcelable {
    companion object {
        fun DocumentSnapshot.toSpecificData(): SpecificDataModel? {
            return try {
                val url: String = getString("article_url")!!
                val questions: List<String> = get("question") as List<String>
                SpecificDataModel(url, questions)
            } catch (e: Exception) {
                Log.e(TAG,"Error converting data",e)
                null
            }
        }

        private const val TAG = "Article"
    }
}