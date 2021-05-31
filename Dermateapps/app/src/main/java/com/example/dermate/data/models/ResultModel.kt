package com.example.dermate.data.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ResultModel(
    var id: List<Int>?,
    var image: Uri?
) : Parcelable