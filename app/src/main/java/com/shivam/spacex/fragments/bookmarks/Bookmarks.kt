package com.shivam.spacex.fragments.bookmarks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shivam.spacex.R
import com.shivam.spacex.databinding.FragmentBookmarksBinding
import com.shivam.spacex.databinding.FragmentShipListingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Bookmarks : Fragment() {
    private var _binding: FragmentBookmarksBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentBookmarksBinding.inflate(inflater,container,false)
       return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}