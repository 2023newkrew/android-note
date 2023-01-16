package com.survivalcoding.noteapp.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.survivalcoding.noteapp.Config
import com.survivalcoding.noteapp.Config.Companion.EXTRA_KEY_FRAGMENT
import com.survivalcoding.noteapp.Config.Companion.EXTRA_KEY_NOTE
import com.survivalcoding.noteapp.Config.Companion.FRAGMENT_CODE_EDIT
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.FragmentListBinding
import com.survivalcoding.noteapp.presentation.DetailActivity
import com.survivalcoding.noteapp.presentation.adapter.NoteListAdapter
import com.survivalcoding.noteapp.presentation.event.UserEvent
import com.survivalcoding.noteapp.presentation.viewmodel.ListViewModel
import com.survivalcoding.noteapp.presentation.viewmodel.ListViewModel.Companion.ListViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMenu()

        noteListAdapter = NoteListAdapter(::onItemClick, ::onItemRemoveClick)
        val recyclerView = binding.noteRecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = noteListAdapter

        // db
        lifecycleScope.launch {
            val orderCode = viewModel.convertOrderCodeToKey(
                viewModel.state.value.orderCode,
                viewModel.state.value.isReversed
            )
            viewModel.getNotes(orderCode).collect(noteListAdapter::submitList)
        }

        // sort
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    val orderCode = viewModel.convertOrderCodeToKey(
                        state.orderCode,
                        state.isReversed
                    )

                    val recyclerViewState = recyclerView.layoutManager?.onSaveInstanceState()
                    noteListAdapter.submitList(viewModel.getNotes(orderCode).first()) {
                        recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
                    }
                }
            }
        }

        // user event
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collectLatest { event ->
                    when (event) {
                        is UserEvent.ShowSnackBar -> {
                            val snackBar = Snackbar.make(
                                binding.root,
                                event.message,
                                Snackbar.LENGTH_SHORT
                            )

                            if (event.message == getString(R.string.message_delete)) {
                                snackBar.setAction(R.string.undo) { viewModel.restoreNote() }
                            }
                            snackBar.show()
                        }
                    }
                }
            }
        }

        binding.radioGroupOrderKey.check(
            when (viewModel.state.value.orderCode) {
                Config.ORDER_CODE_TITLE -> R.id.radio_title
                Config.ORDER_CODE_DATE -> R.id.radio_date
                Config.ORDER_CODE_COLOR -> R.id.radio_color
                else -> R.id.radio_title
            }
        )
        binding.radioGroupOrderReverse.check(
            when (viewModel.state.value.isReversed) {
                true -> R.id.radio_descending
                false -> R.id.radio_ascending
            }
        )

        binding.radioGroupOrderKey.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_title -> viewModel.changeOrder(
                    Config.ORDER_CODE_TITLE,
                    viewModel.state.value.isReversed
                )
                R.id.radio_date -> viewModel.changeOrder(
                    Config.ORDER_CODE_DATE,
                    viewModel.state.value.isReversed
                )
                R.id.radio_color -> viewModel.changeOrder(
                    Config.ORDER_CODE_COLOR,
                    viewModel.state.value.isReversed
                )
            }
        }
        binding.radioGroupOrderReverse.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_ascending -> viewModel.changeOrder(
                    orderCode = viewModel.state.value.orderCode,
                    isReversed = false
                )
                R.id.radio_descending -> viewModel.changeOrder(
                    orderCode = viewModel.state.value.orderCode,
                    isReversed = true
                )
            }
        }
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
                        binding.layoutRadio.visibility =
                            if (binding.layoutRadio.visibility == VISIBLE) GONE
                            else VISIBLE
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

    private fun onItemClick(position: Int) {
        val currentList = noteListAdapter.currentList.toMutableList()
        val note = currentList[position]
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_FRAGMENT, FRAGMENT_CODE_EDIT)
            putExtra(EXTRA_KEY_NOTE, note)
        }
        startActivity(intent)
    }
}