package com.example.dermate.data.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class QuestionResultModel(
    val uri: Uri?,
    val diseaseName : String?
) : Parcelable