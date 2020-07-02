package com.collegecommunity.androidapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.collegecommunity.androidapp.models.Post
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase

class BlogViewModel: ViewModel() {
    private val db = Firebase.firestore

    init {
        val settings = firestoreSettings {
            isPersistenceEnabled = true
            cacheSizeBytes = FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED
        }
        db.firestoreSettings = settings
    }

    private val postsRef = db.collection("posts")
    private val _posts = MutableLiveData<List<Post>>().apply {
        postsRef.get()
            .addOnSuccessListener {
                value = it?.toObjects(Post::class.java)
                Log.i("_posts", value.toString())
            }
            .addOnFailureListener {
                it.printStackTrace()
            }

    }

    val posts: LiveData<List<Post>>
        get() = _posts

    private val _addNewPost = MutableLiveData<Boolean>()
    val addNewPost: LiveData<Boolean>
        get() = _addNewPost

    fun onFabClick() {
        _addNewPost.value = true
    }

}