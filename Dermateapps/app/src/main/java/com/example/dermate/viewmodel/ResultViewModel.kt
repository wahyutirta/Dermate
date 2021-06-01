package com.example.dermate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dermate.data.models.SpecificDataModel
import com.example.dermate.data.source.remote.firebase.firestore.FireStoreService
import kotlinx.coroutines.launch

class ResultViewModel : ViewModel() {

    val data = MutableLiveData<SpecificDataModel>()

    fun getDetailedData(name: String): LiveData<SpecificDataModel> {
        viewModelScope.launch {
            data.value = FireStoreService.getSpecificDataByDisease(name)
        }
        return data
    }
}