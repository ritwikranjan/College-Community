package com.collegecommunity.androidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import com.collegecommunity.androidapp.firebaselivedata.FirebaseUserLiveData
import com.firebase.ui.auth.AuthUI

internal const val RC_SIGN_IN: Int = 101

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            FirebaseUserLiveData().observe(this, Observer {
                it?.let {
                    startActivity(Intent(this,MainActivity::class.java))
                }
                if (it == null){
                    launchLoginActivity()
                }
            })

        },3000)
    }

    private fun launchLoginActivity() {
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
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }
}