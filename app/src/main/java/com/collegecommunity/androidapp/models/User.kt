package com.collegecommunity.androidapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val username: String = "Anonymous",
    val online: Boolean = true
) : Parcelable