package com.collegecommunity.androidapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.collegecommunity.androidapp.MainActivity
import com.collegecommunity.androidapp.R
import com.collegecommunity.androidapp.RC_SIGN_IN
import com.collegecommunity.androidapp.databinding.FragmentBlogBinding
import com.collegecommunity.androidapp.firebaselivedata.FirebaseUserLiveData
import com.collegecommunity.androidapp.viewmodels.BlogViewModel
import com.firebase.ui.auth.AuthUI

class BlogFragment : Fragment() {

    private lateinit var binding: FragmentBlogBinding
    private lateinit var viewModel: BlogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBlogBinding.inflate(inflater)
        FirebaseUserLiveData().observe(viewLifecycleOwner, Observer {
            if (it == null){
                launchSignInFlow()
            }
        })
        viewModel = ViewModelProvider(this).get(BlogViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.addNewPost.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    
                }
            }
        })



        return binding.root
    }

    private fun launchSignInFlow() {
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