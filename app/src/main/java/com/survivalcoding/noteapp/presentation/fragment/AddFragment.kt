package com.survivalcoding.noteapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.survivalcoding.noteapp.databinding.FragmentAddBinding
import com.survivalcoding.noteapp.presentation.viewmodel.AddViewModel
import com.survivalcoding.noteapp.presentation.viewmodel.AddViewModel.Companion.AddViewModelFactory

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddViewModel by viewModels { AddViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    class OnColorButtonClickListener : OnClickListener {
        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }
}