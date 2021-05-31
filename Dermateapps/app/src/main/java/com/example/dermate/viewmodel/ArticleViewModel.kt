package com.example.dermate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dermate.data.models.ArticleModel
import com.example.dermate.data.source.remote.firebase.firestore.FireStoreService
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {
    private val data = MutableLiveData<ArticleModel>()
    fun articleData(): LiveData<ArticleModel> {
        viewModelScope.launch {
            data.value = FireStoreService.getListArticle()
        }
        return data
    }

}