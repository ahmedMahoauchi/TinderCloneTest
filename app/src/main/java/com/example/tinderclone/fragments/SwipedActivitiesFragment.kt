package com.example.tinderclone.fragments

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tinderclone.R
import com.example.tinderclone.databinding.FragmentSwipedActivitiesListDialogItemBinding
import com.example.tinderclone.databinding.FragmentSwipedActivitiesListDialogBinding

class SwipedActivitiesFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSwipedActivitiesListDialogBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSwipedActivitiesListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}