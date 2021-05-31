package com.example.dermate.data.source.remote.firebase.firestore

import android.util.Log
import com.example.dermate.data.models.ArticleModel
import com.example.dermate.data.models.ArticleModel.Companion.toArticle
import com.example.dermate.data.models.SpecificDataModel
import com.example.dermate.data.models.SpecificDataModel.Companion.toSpecificData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Exception

object FireStoreService {
    private const val TAG = "FirebaseProfileService"

    suspend fun getListArticle():ArticleModel?{
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("article").document("data").get().await().toArticle()
        }catch (e : Exception){
            Log.e(TAG, "Error getting data", e)
            null
        }
    }

    suspend fun getSpecificDataByDisease(disease : String) : SpecificDataModel?{
        val db = FirebaseFirestore.getInstance()
        return try{
            db.collection("dermatedatabase").document(disease).get().await().toSpecificData()
        }catch (e : Exception){
            Log.e(TAG, "Error getting data", e)
            null
        }
    }
}