package com.survivalcoding.noteapp.presentation.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.survivalcoding.noteapp.core.extension.app
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentNotesBinding
import com.survivalcoding.noteapp.domain.util.NoteOrder
import com.survivalcoding.noteapp.domain.util.OrderType
import com.survivalcoding.noteapp.presentation.add_edit_note.AddEditNoteFragment
import com.survivalcoding.noteapp.presentation.notes.adapter.NoteListAdapter

class NotesFragment : Fragment(R.layout.fragment_notes) {
    private var _binding: FragmentNotesBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<NotesViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
                        return NotesViewModel(app.noteUseCases) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel Class")
                }
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        lifecycle.addObserver(viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notesAdapter = NoteListAdapter(
            onDelete = { note ->
                viewModel.onUserEvent(NotesEvent.DeleteNote(note))

                Snackbar.make(binding.root, "노트가 삭제되었습니다", Snackbar.LENGTH_LONG)
                    .setAction("취소") {
                        viewModel.onUserEvent(NotesEvent.RestoreNote)
                    }
                    .setAnchorView(binding.addButton)
                    .show()
            },
            onSelect = { note ->
                parentFragmentManager.commit {
                    replace<AddEditNoteFragment>(
                        R.id.fragment_container,
                        args = bundleOf("noteId" to note.id),
                    )
                    setReorderingAllowed(true)
                    addToBackStack("addEditNote")
                }
            }
        )

        binding.notesList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notesAdapter
        }

        binding.addButton.setOnClickListener {
            parentFragmentManager.commit {
                replace<AddEditNoteFragment>(R.id.fragment_container)
                setReorderingAllowed(true)
                addToBackStack("addEditNote")
            }
        }

        binding.titleRadioButton.setOnClickListener {
            viewModel.onUserEvent(NotesEvent.Order(NoteOrder.Title(viewModel.noteOrder.orderType)))
        }
        binding.dateRadioButton.setOnClickListener {
            viewModel.onUserEvent(NotesEvent.Order(NoteOrder.Date(viewModel.noteOrder.orderType)))
        }
        binding.colorRadioButton.setOnClickListener {
            viewModel.onUserEvent(NotesEvent.Order(NoteOrder.Color(viewModel.noteOrder.orderType)))
        }
        binding.ascRadioButton.setOnClickListener {
            viewModel.onUserEvent(NotesEvent.Order(viewModel.noteOrder.copy(OrderType.Ascending)))
        }
        binding.descRadioButton.setOnClickListener {
            viewModel.onUserEvent(NotesEvent.Order(viewModel.noteOrder.copy(OrderType.Descending)))
        }

        binding.menu.setOnClickListener {
            viewModel.onUserEvent(NotesEvent.ToggleOrderSection)
        }

        viewModel.state.asLiveData().observe(viewLifecycleOwner) { state ->
            notesAdapter.submitList(state.notes)

            binding.radioGroup.isVisible = state.isOrderSectionVisible
            binding.radioGroup2.isVisible = state.isOrderSectionVisible
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(viewModel)
        _binding = null
    }
}