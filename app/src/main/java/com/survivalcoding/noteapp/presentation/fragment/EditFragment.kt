package com.survivalcoding.noteapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.survivalcoding.noteapp.databinding.FragmentEditBinding
import com.survivalcoding.noteapp.presentation.viewmodel.EditViewModel
import com.survivalcoding.noteapp.presentation.viewmodel.EditViewModel.Companion.EditViewModelFactory

class EditFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditViewModel by viewModels { EditViewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }
}