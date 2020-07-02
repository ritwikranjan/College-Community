package com.collegecommunity.androidapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    val userId: String = "",
    val postContent: String = "",
    val postType: String = "",
    val postImageUri: String? = null,
    val postUpVotes: Int = 0,
    val postDownVotes: Int = 0
) : Parcelable