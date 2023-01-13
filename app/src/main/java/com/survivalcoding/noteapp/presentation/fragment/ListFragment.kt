package com.survivalcoding.noteapp.presentation.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentListBinding
import com.survivalcoding.noteapp.presentation.adapter.NoteListAdapter
import com.survivalcoding.noteapp.presentation.viewmodel.ListViewModel
import com.survivalcoding.noteapp.presentation.viewmodel.ListViewModel.Companion.ListViewModelFactory
import kotlinx.coroutines.launch

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListViewModel by viewModels { ListViewModelFactory }
    private lateinit var noteListAdapter: NoteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupMenu()

        noteListAdapter = NoteListAdapter(::onItemRemoveClick)
        val recyclerView = binding.noteRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = noteListAdapter

        lifecycleScope.launch {
            viewModel.getNotes().collect(noteListAdapter::submitList)
        }

        return root
    }

    private fun setupMenu() {
        binding.toolbar.addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) = Unit

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.sort -> {
                        // TODO
                    }
                }
                return true
            }
        }, requireActivity(), Lifecycle.State.STARTED)
    }

    private fun onItemRemoveClick(position: Int) {
        val currentList = noteListAdapter.currentList.toMutableList()
        val note = currentList[position]
        viewModel.deleteNote(note)
    }
}