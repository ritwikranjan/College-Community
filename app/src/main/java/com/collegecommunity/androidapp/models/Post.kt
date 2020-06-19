package com.collegecommunity.androidapp.models

data class Post(val userId: String,
                val postTitle: String,
                val postContent: String,
                val postType: String,
                val postSubTitle: String?,
                val postUpVotes: Int = 0,
                val postDownVotes: Int = 0)