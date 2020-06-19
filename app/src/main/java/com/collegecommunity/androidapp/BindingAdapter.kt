package com.collegecommunity.androidapp

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.collegecommunity.androidapp.models.Post

@BindingAdapter("postsList")
public fun bindPosts(recyclerView: RecyclerView, data: List<Post>){
    val adapter = recyclerView.adapter

}