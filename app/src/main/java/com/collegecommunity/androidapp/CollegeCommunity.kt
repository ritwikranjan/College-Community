package com.collegecommunity.androidapp

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CollegeCommunity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val mUser = FirebaseAuth.getInstance().currentUser
        val mDatabaseReference =
            FirebaseDatabase.getInstance().reference.child("users").child(mUser!!.uid)
        mDatabaseReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    mDatabaseReference.child("online").onDisconnect()
                        .setValue(false)
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }
}