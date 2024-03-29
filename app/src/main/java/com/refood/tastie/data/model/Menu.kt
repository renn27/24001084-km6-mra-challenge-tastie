package com.refood.tastie.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    val id: String? = UUID.randomUUID().toString(),
    val name: String,
    val price: Double,
    val imagePictUrl: String,
    val description: String,
    val location: String,
    val urlLocation: String
) : Parcelable