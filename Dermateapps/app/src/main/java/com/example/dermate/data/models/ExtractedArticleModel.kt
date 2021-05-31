package com.example.dermate.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExtractedArticleModel(
    var title: String?,
    var sitename: String?,
    var imageUrl: String?

) : Parcelable
