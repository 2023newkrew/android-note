package com.survivalcoding.noteapp.presentation.add_edit_note

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.google.android.material.snackbar.Snackbar
import com.survivalcoding.noteapp.App
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.core.extension.app
import com.survivalcoding.noteapp.databinding.FragmentAddEditNoteBinding
import com.survivalcoding.noteapp.ui.colors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddEditNoteFragment : Fragment(R.layout.fragment_add_edit_note) {
    private var _binding: FragmentAddEditNoteBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: AddEditNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(
            this,
            AddEditNoteViewModelFactory(
                this,
                app,
                arguments ?: savedInstanceState
            )
        )[AddEditNoteViewModel::class.java]

        _binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            viewModel.onEvent(
                AddEditNoteEvent.SaveNote(
                    title = binding.titleEditText.text.toString(),
                    content = binding.contentEditText.text.toString(),
                )
            )
        }

        val circles = listOf(
            Pair(binding.imageView, ResourcesCompat.getColor(resources, R.color.roseBud, null)),
            Pair(binding.imageView2, ResourcesCompat.getColor(resources, R.color.primrose, null)),
            Pair(binding.imageView3, ResourcesCompat.getColor(resources, R.color.wisteria, null)),
            Pair(binding.imageView4, ResourcesCompat.getColor(resources, R.color.skyBlue, null)),
            Pair(binding.imageView5, ResourcesCompat.getColor(resources, R.color.illusion, null)),
        )

        circles.first().first.isSelected = true

        circles.forEach { circle ->
            circle.first.setOnClickListener { view ->
                view.isSelected = true
//
                circles.filter { it.first.id != view.id }
                    .forEach { it.first.isSelected = false }

                viewModel.onEvent(AddEditNoteEvent.ChangeColor(circle.second))
            }
        }

        viewModel.state.asLiveData().observe(viewLifecycleOwner, Observer { state ->
            binding.titleEditText.setText(state.title)
            binding.contentEditText.setText(state.content)

            val startDrawable = ColorDrawable(colors.first())
            val endDrawable = ColorDrawable(state.color)
            val transitionDrawable = TransitionDrawable(arrayOf(startDrawable, endDrawable))
            binding.background.background = transitionDrawable
            transitionDrawable.startTransition(500)
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collectLatest { event ->
                    when (event) {
                        is UiEvent.SaveNote -> parentFragmentManager.popBackStack()
                        is UiEvent.ShowSnackBar -> {
                            Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG)
                                .setAnchorView(binding.saveButton)
                                .show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class AddEditNoteViewModelFactory(
        owner: SavedStateRegistryOwner,
        val app: App,
        defaultArgs: Bundle? = null
    ) :
        AbstractSavedStateViewModelFactory(
            owner, defaultArgs
        ) {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            if (modelClass.isAssignableFrom(AddEditNoteViewModel::class.java)) {
                val useCases = app.noteUseCases
                val repository = app.repository

                return AddEditNoteViewModel(repository, useCases, handle) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}