@file:Suppress("UNCHECKED_CAST")

package com.example.dermate.data.models

import android.os.Parcelable
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpecificDataModel(
    val url: String?,
    val questions: List<String>?,
    val solutions: List<String>?,
    val treatmentSource: String?
) : Parcelable {
    companion object {
        fun DocumentSnapshot.toSpecificData(): SpecificDataModel? {
            return try {
                val url = get("article_url") as? String
                val questions: List<String>? = get("question") as? List<String>
                val solutions: List<String>? = get("solution") as? List<String>
                val treatmentSource = get("treatment_source") as? String
                SpecificDataModel(url, questions, solutions, treatmentSource)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting data", e)
                null
            }
        }

        private const val TAG = "Data specified"
    }
}