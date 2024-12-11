package com.septiar.news_on

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val title: String,
    val overview: String,
    val description: String,
    val description2: String,
    val image: String,
    val date: String,
    val link: String,
) : Parcelable
