package com.example.dermate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dermate.data.models.SpecificDataModel
import com.example.dermate.data.source.remote.firebase.firestore.FireStoreService
import kotlinx.coroutines.launch

class QuestionViewModel : ViewModel() {
    private val data1 = MutableLiveData<SpecificDataModel>()
    private val data2 = MutableLiveData<SpecificDataModel>()
    private val data3 = MutableLiveData<SpecificDataModel>()

    fun getSpecifiedData1(diseaseName: String): LiveData<SpecificDataModel> {
        viewModelScope.launch {
            data1.value = FireStoreService.getSpecificDataByDisease(diseaseName)
        }
        return data1
    }
    fun getSpecifiedData2(diseaseName: String): LiveData<SpecificDataModel> {
        viewModelScope.launch {
            data2.value = FireStoreService.getSpecificDataByDisease(diseaseName)
        }
        return data2
    }
    fun getSpecifiedData3(diseaseName: String): LiveData<SpecificDataModel> {
        viewModelScope.launch {
            data3.value = FireStoreService.getSpecificDataByDisease(diseaseName)
        }
        return data3
    }
}