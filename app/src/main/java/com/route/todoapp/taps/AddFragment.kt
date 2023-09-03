package com.route.todoapp.taps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.todoapp.databinding.FragmentAddBinding

class AddFragment: BottomSheetDialogFragment() {
    lateinit var viewBinding: FragmentAddBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddBinding.inflate(inflater,container,false)
        return viewBinding.root
    }
}