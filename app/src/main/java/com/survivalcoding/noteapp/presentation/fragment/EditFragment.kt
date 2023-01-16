package com.survivalcoding.noteapp.presentation.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.survivalcoding.noteapp.Config
import com.survivalcoding.noteapp.Config.Companion.dualPane
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentEditBinding
import com.survivalcoding.noteapp.domain.model.Note
import com.survivalcoding.noteapp.presentation.util.EditorUtil
import com.survivalcoding.noteapp.presentation.viewmodel.EditViewModel
import com.survivalcoding.noteapp.presentation.viewmodel.EditViewModel.Companion.EditViewModelFactory
import kotlinx.coroutines.launch

class EditFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditViewModel by viewModels { EditViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val note: Note =
            // case version code T 33
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireActivity().intent.getParcelableExtra(Config.EXTRA_KEY_NOTE, Note::class.java)
            } else {
                requireActivity().intent.getParcelableExtra(Config.EXTRA_KEY_NOTE)
            } ?: Note()

        binding.includeEditor.titleEditText.setText(note.title)
        binding.includeEditor.contentEditText.setText(note.content)
        viewModel.changeColor(note.colorCode)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    selectColorButton(state.colorCode)
                }
            }
        }

        binding.includeEditor.colorButtonRed.setOnClickListener {
            viewModel.changeColor(Config.COLOR_CODE_RED)
        }
        binding.includeEditor.colorButtonYellow.setOnClickListener {
            viewModel.changeColor(Config.COLOR_CODE_YELLOW)
        }
        binding.includeEditor.colorButtonPurple.setOnClickListener {
            viewModel.changeColor(Config.COLOR_CODE_PURPLE)
        }
        binding.includeEditor.colorButtonBlue.setOnClickListener {
            viewModel.changeColor(Config.COLOR_CODE_BLUE)
        }
        binding.includeEditor.colorButtonPink.setOnClickListener {
            viewModel.changeColor(Config.COLOR_CODE_PINK)
        }
        binding.includeEditor.saveFab.setOnClickListener {
            val event = EditorUtil.checkEditTextsNotEmpty(
                binding.includeEditor.titleEditText,
                binding.includeEditor.contentEditText
            )
            if (event == null) {
                viewModel.updateNote(
                    note.copy(
                        title = binding.includeEditor.titleEditText.text.toString(),
                        content = binding.includeEditor.contentEditText.text.toString(),
                        colorCode = viewModel.state.value.colorCode,
                        time = System.currentTimeMillis()
                    )
                )

                if (dualPane) {
                    val imm: InputMethodManager =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.root.windowToken, 0)

                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .remove(this)
                        .commit()
                } else {
                    requireActivity().finish()
                }
            } else {
                val snackBar = Snackbar.make(
                    binding.includeEditor.layoutEditor,
                    event.message,
                    Snackbar.LENGTH_SHORT
                )
                snackBar.show()
            }
        }
    }

    private fun selectColorButton(colorCode: Int) {
        when (colorCode) {
            Config.COLOR_CODE_RED -> changeThemeColor(R.color.note_red)
            Config.COLOR_CODE_YELLOW -> changeThemeColor(R.color.note_yellow)
            Config.COLOR_CODE_PURPLE -> changeThemeColor(R.color.note_purple)
            Config.COLOR_CODE_BLUE -> changeThemeColor(R.color.note_blue)
            Config.COLOR_CODE_PINK -> changeThemeColor(R.color.note_pink)
        }

        binding.includeEditor.colorButtonRed.setImageResource(
            if (colorCode == Config.COLOR_CODE_RED) R.drawable.drawable_circle_border
            else R.drawable.drawable_circle
        )
        binding.includeEditor.colorButtonYellow.setImageResource(
            if (colorCode == Config.COLOR_CODE_YELLOW) R.drawable.drawable_circle_border
            else R.drawable.drawable_circle
        )
        binding.includeEditor.colorButtonPurple.setImageResource(
            if (colorCode == Config.COLOR_CODE_PURPLE) R.drawable.drawable_circle_border
            else R.drawable.drawable_circle
        )
        binding.includeEditor.colorButtonBlue.setImageResource(
            if (colorCode == Config.COLOR_CODE_BLUE) R.drawable.drawable_circle_border
            else R.drawable.drawable_circle
        )
        binding.includeEditor.colorButtonPink.setImageResource(
            if (colorCode == Config.COLOR_CODE_PINK) R.drawable.drawable_circle_border
            else R.drawable.drawable_circle
        )
    }

    private fun changeThemeColor(colorResId: Int) {
        binding.includeEditor.layoutEditor.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                colorResId
            )
        )
        if (!dualPane) {
            requireActivity().window.statusBarColor =
                ContextCompat.getColor(requireContext(), colorResId)
        }
        requireActivity().window.navigationBarColor =
            ContextCompat.getColor(requireContext(), colorResId)
    }
}