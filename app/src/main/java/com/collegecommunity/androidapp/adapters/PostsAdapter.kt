package com.collegecommunity.androidapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.collegecommunity.androidapp.databinding.PostLayoutBinding
import com.collegecommunity.androidapp.models.Post
import com.collegecommunity.androidapp.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PostsAdapter: ListAdapter<Post,PostsAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PostLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }




    object DiffCallBack: DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.postContent == newItem.postContent
        }

    }


    class ViewHolder(private val binding: PostLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post?) {
            val user = fetchUser(post)
            binding.post = post
            binding.user = user
            binding.executePendingBindings()
        }

        private fun fetchUser(post: Post?): User {
            var user: User? = null
            FirebaseDatabase.getInstance().getReference("users").child(post?.userId ?: "exception")
                .addValueEventListener(
                    object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError) {
                            println(error.message)
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            user = snapshot.getValue(User::class.java)
                        }

                    })
            return user ?: User("exception", true)
        }

    }

}