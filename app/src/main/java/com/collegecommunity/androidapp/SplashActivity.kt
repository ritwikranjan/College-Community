package com.collegecommunity.androidapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.collegecommunity.androidapp.firebaselivedata.FirebaseUserLiveData
import com.collegecommunity.androidapp.models.User
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.database.FirebaseDatabase

internal const val RC_SIGN_IN: Int = 101

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            FirebaseUserLiveData().observe(this, Observer {
                it?.let {
                    Log.d("Check it", it.displayName ?: "absjdf")
                    FirebaseDatabase.getInstance()
                        .getReference("users")
                        .child(it.uid)
                        .setValue(
                            User(
                                it.displayName ?: "exception",
                                true
                            )
                        )
                    startActivity(Intent(this, MainActivity::class.java))
                }
                if (it == null){
                    launchLoginActivity()
                }
            })

        },3000)
    }

    private fun launchLoginActivity() {
        FirebaseUserLiveData().removeObservers(this)
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.AnonymousBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAlwaysShowSignInMethodScreen(true)
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                FirebaseUserLiveData().observe(this, Observer {
                    it?.let {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                    if (it == null) {
                        launchLoginActivity()
                    }
                })
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}

