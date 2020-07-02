package com.collegecommunity.androidapp.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.collegecommunity.androidapp.firebaselivedata.FirebaseUserLiveData
import com.collegecommunity.androidapp.models.Post
import com.google.firebase.database.ServerValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class NewPostViewModel : ViewModel() {

    private val userId = FirebaseUserLiveData().value

    private val db = Firebase.firestore
    private val postsRef = db.collection("posts")


    private val storage = Firebase.storage
    private val storageRef = storage.reference
    private val postImageStorageRef = storageRef.child("images")

    val postContent = MutableLiveData<String>("")
    private val postImageUri = MutableLiveData<String?>(null)

    val contentValid = MediatorLiveData<Boolean>().apply {
        addSource(postContent) {
            value = it.trim().isNotEmpty()
        }
    }

    private val _createdPost = MutableLiveData<Post?>(null)
    val createdPost: LiveData<Post?>
        get() = _createdPost

    private val _postWithImg = MutableLiveData<Boolean>(false)
    val postWithImg: LiveData<Boolean>
        get() = _postWithImg

    fun onPostClick() {
        if (_postWithImg.value == true) {
            postToDatabase(
                Post(
                    userId = userId?.uid ?: "",
                    postContent = postContent.value ?: "",
                    postType = "img",
                    postImageUri = postImageUri.value
                )
            )
        } else {
            postToDatabase(
                Post(
                    userId = userId?.uid ?: "",
                    postContent = postContent.value ?: "",
                    postType = "text",
                    postImageUri = null
                )
            )
        }
    }

    fun onAddImgClick() {
        _postWithImg.value = true
    }

    fun uploadImgToFirebaseStorage(imageUri: Uri) {
        val imageRef = postImageStorageRef.child(imageUri.lastPathSegment.toString())
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                postImageUri.value = task.result.toString()
            }
        }
    }

    private fun postToDatabase(post: Post) {
        postsRef.add(post)
            .addOnSuccessListener {
                _createdPost.value = post
                it.update("time", ServerValue.TIMESTAMP)
            }
    }


}