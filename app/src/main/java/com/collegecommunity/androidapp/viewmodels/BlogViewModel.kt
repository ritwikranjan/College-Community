package com.collegecommunity.androidapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.collegecommunity.androidapp.models.Post
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BlogViewModel: ViewModel() {

    private val db = Firebase.firestore
    private val postsRef = db.collection("posts")

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>>
        get() {
            if (_posts.value == null){
                postsRef.get()
                    .addOnSuccessListener {
                        _posts.value = it?.toObjects(Post::class.java)
                    }
            }
            return _posts
        }

    fun postToDatabase(post: Post){
        postsRef.add(post)
            .addOnSuccessListener {

            }
    }

    private val _addNewPost = MutableLiveData<Boolean>()
    val addNewPost: LiveData<Boolean>
            get() = _addNewPost


    fun onFabClick(){
        _addNewPost.value = true
    }

}