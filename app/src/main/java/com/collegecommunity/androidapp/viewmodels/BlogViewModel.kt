package com.collegecommunity.androidapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.collegecommunity.androidapp.models.Post
import com.google.firebase.database.*

class BlogViewModel: ViewModel() {

    val _posts = MutableLiveData<List<Post>>()

    val posts: LiveData<List<Post>>
        get() {
            if (_posts.value == null){
                FirebaseDatabase.getInstance()
                    .getReference("Posts")
                    .addValueEventListener(object : ValueEventListener{
                        override fun onCancelled(error: DatabaseError) {
                            Log.e("DatabaseError", error.details)
                        }
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()){
                                _posts.postValue(snapshot.children.map {
                                    it.let {
                                        return@let Post(it.child("userId").toString(), it.child("postTitle").toString(), it.child("postContent").toString(), it.child("postType").toString(),it.child("postSubTitle").toString())
                                    }
                                })
                            }
                        }
                    })
            }

            return _posts
        }


}