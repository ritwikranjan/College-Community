package com.collegecommunity.androidapp.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.collegecommunity.androidapp.databinding.FragmentNewPostBinding
import com.collegecommunity.androidapp.viewmodels.NewPostViewModel

private const val SELECT_PICTURE: Int = 101

class NewPostFragment : Fragment() {

    private lateinit var binding: FragmentNewPostBinding
    private lateinit var viewModel: NewPostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewPostBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(NewPostViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.createdPost.observe(viewLifecycleOwner, Observer {
            it?.let {
                NavHostFragment.findNavController(this)
                    .navigate(NewPostFragmentDirections.actionNewPostFragmentToBlogNav())
            }
        })

        viewModel.postWithImg.observe(viewLifecycleOwner, Observer {
            if (it) {
                val galleryIntent = Intent()
                galleryIntent.type = "image/*"
                galleryIntent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(
                    Intent.createChooser(galleryIntent, "Upload Image"),
                    SELECT_PICTURE
                )
            }
        })
        binding.viewModel = viewModel

        return binding.root

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                val selectedImageUri = data?.data
                viewModel.uploadImgToFirebaseStorage(selectedImageUri!!)
            }
        }
    }
}