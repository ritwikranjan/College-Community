package com.collegecommunity.androidapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.collegecommunity.androidapp.adapters.PostsAdapter
import com.collegecommunity.androidapp.databinding.FragmentBlogBinding
import com.collegecommunity.androidapp.viewmodels.BlogViewModel

class BlogFragment : Fragment() {

    private lateinit var binding: FragmentBlogBinding
    private lateinit var viewModel: BlogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBlogBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(BlogViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.addNewPost.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    NavHostFragment.findNavController(this)
                        .navigate(BlogFragmentDirections.actionBlogNavToNewPostFragment())
                }
            }
        })

        binding.lifecycleOwner = viewLifecycleOwner

        binding.postsList.adapter = PostsAdapter()

        return binding.root
    }


}