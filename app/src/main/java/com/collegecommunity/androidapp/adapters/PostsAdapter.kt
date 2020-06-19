package com.collegecommunity.androidapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.collegecommunity.androidapp.databinding.FragmentBlogBinding
import com.collegecommunity.androidapp.databinding.PostLayoutBinding
import com.collegecommunity.androidapp.models.Post

class PostsAdapter: ListAdapter<Post,PostsAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }




    object DiffCallBack: DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.postContent == newItem.postContent
        }

    }


    class ViewHolder(val binding: PostLayoutBinding):RecyclerView.ViewHolder(binding.root) {

    }

}