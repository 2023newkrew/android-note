package com.survivalcoding.noteapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.survivalcoding.noteapp.Config.Companion.COLOR_CODE_BLUE
import com.survivalcoding.noteapp.Config.Companion.COLOR_CODE_PINK
import com.survivalcoding.noteapp.Config.Companion.COLOR_CODE_PURPLE
import com.survivalcoding.noteapp.Config.Companion.COLOR_CODE_RED
import com.survivalcoding.noteapp.Config.Companion.COLOR_CODE_YELLOW
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentAddBinding
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.presentation.util.EditorUtil.Companion.checkEditTextsNotEmpty
import com.survivalcoding.noteapp.presentation.viewmodel.AddViewModel
import com.survivalcoding.noteapp.presentation.viewmodel.AddViewModel.Companion.AddViewModelFactory
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    selectColorButton(state.colorCode)
                }
            }
        }

        binding.includeEditor.colorButtonRed.setOnClickListener {
            viewModel.changeColor(COLOR_CODE_RED)
        }
        binding.includeEditor.colorButtonYellow.setOnClickListener {
            viewModel.changeColor(COLOR_CODE_YELLOW)
        }
        binding.includeEditor.colorButtonPurple.setOnClickListener {
            viewModel.changeColor(COLOR_CODE_PURPLE)
        }
        binding.includeEditor.colorButtonBlue.setOnClickListener {
            viewModel.changeColor(COLOR_CODE_BLUE)
        }
        binding.includeEditor.colorButtonPink.setOnClickListener {
            viewModel.changeColor(COLOR_CODE_PINK)
        }
        binding.includeEditor.saveFab.setOnClickListener {
            val event = checkEditTextsNotEmpty(
                binding.includeEditor.titleEditText,
                binding.includeEditor.contentEditText
            )
            if (event == null) {
                viewModel.saveNote(
                    Note(
                        title = binding.includeEditor.titleEditText.text.toString(),
                        content = binding.includeEditor.contentEditText.text.toString(),
                        colorCode = viewModel.state.value.colorCode,
                        time = System.currentTimeMillis()
                    )
                )
                requireActivity().finish()
            } else {
                val snackBar = Snackbar.make(
                    binding.root,
                    event.message,
                    Snackbar.LENGTH_SHORT
                )
                snackBar.show()
            }
        }

        return root
    }

    private fun selectColorButton(colorCode: Int) {
        when (colorCode) {
            COLOR_CODE_RED -> {
                binding.includeEditor.layoutEditor.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.note_red
                    )
                )
                requireActivity().window.statusBarColor =
                    ContextCompat.getColor(requireContext(), R.color.note_red)
                requireActivity().window.navigationBarColor =
                    ContextCompat.getColor(requireContext(), R.color.note_red)
            }
            COLOR_CODE_YELLOW -> {
                binding.includeEditor.layoutEditor.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.note_yellow
                    )
                )
                requireActivity().window.statusBarColor =
                    ContextCompat.getColor(requireContext(), R.color.note_yellow)
                requireActivity().window.navigationBarColor =
                    ContextCompat.getColor(requireContext(), R.color.note_yellow)
            }
            COLOR_CODE_PURPLE -> {
                binding.includeEditor.layoutEditor.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.note_purple
                    )
                )
                requireActivity().window.statusBarColor =
                    ContextCompat.getColor(requireContext(), R.color.note_purple)
                requireActivity().window.navigationBarColor =
                    ContextCompat.getColor(requireContext(), R.color.note_purple)
            }
            COLOR_CODE_BLUE -> {
                binding.includeEditor.layoutEditor.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.note_blue
                    )
                )
                requireActivity().window.statusBarColor =
                    ContextCompat.getColor(requireContext(), R.color.note_blue)
                requireActivity().window.navigationBarColor =
                    ContextCompat.getColor(requireContext(), R.color.note_blue)
            }
            COLOR_CODE_PINK -> {
                binding.includeEditor.layoutEditor.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.note_pink
                    )
                )
                requireActivity().window.statusBarColor =
                    ContextCompat.getColor(requireContext(), R.color.note_pink)
                requireActivity().window.navigationBarColor =
                    ContextCompat.getColor(requireContext(), R.color.note_pink)
            }
        }

        binding.includeEditor.colorButtonRed.setImageResource(
            if (colorCode == COLOR_CODE_RED) R.drawable.drawable_circle_border
            else R.drawable.drawable_circle
        )
        binding.includeEditor.colorButtonYellow.setImageResource(
            if (colorCode == COLOR_CODE_YELLOW) R.drawable.drawable_circle_border
            else R.drawable.drawable_circle
        )
        binding.includeEditor.colorButtonPurple.setImageResource(
            if (colorCode == COLOR_CODE_PURPLE) R.drawable.drawable_circle_border
            else R.drawable.drawable_circle
        )
        binding.includeEditor.colorButtonBlue.setImageResource(
            if (colorCode == COLOR_CODE_BLUE) R.drawable.drawable_circle_border
            else R.drawable.drawable_circle
        )
        binding.includeEditor.colorButtonPink.setImageResource(
            if (colorCode == COLOR_CODE_PINK) R.drawable.drawable_circle_border
            else R.drawable.drawable_circle
        )
    }
}