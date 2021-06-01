package com.example.dermate.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class QuestionDataModel(
    val url: List<String>?,
    val resultCode : Int?
) : Parcelable