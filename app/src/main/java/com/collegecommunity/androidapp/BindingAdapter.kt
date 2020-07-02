package com.collegecommunity.androidapp

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.collegecommunity.androidapp.adapters.PostsAdapter
import com.collegecommunity.androidapp.models.Post

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imgUrl: String?){
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground))
            .into(imageView)
    }
}

@BindingAdapter("postsList")
fun bindPosts(recyclerView: RecyclerView?, data: List<Post>?) {
    val adapter = recyclerView?.adapter as PostsAdapter?
    adapter?.submitList(data)
}

@BindingAdapter("viewVisibilityImg")
fun bindViewsImg(view: View, data: Post?){
    if (data?.postType == "img"){
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("viewVisibilityTxt")
fun bindViewsTxt(view: View, data: Post?){
    if (data?.postType == "text"){
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

