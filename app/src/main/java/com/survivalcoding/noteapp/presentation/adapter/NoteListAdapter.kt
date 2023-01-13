package com.survivalcoding.noteapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.noteapp.Config.Companion.COLOR_CODE_BLUE
import com.survivalcoding.noteapp.Config.Companion.COLOR_CODE_PINK
import com.survivalcoding.noteapp.Config.Companion.COLOR_CODE_PURPLE
import com.survivalcoding.noteapp.Config.Companion.COLOR_CODE_RED
import com.survivalcoding.noteapp.Config.Companion.COLOR_CODE_YELLOW
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.databinding.ItemNoteBinding
import com.survivalcoding.noteapp.domain.model.Note

class NoteListAdapter(
    private val onNoteClick: (Int) -> Unit,
    private val onNoteDelete: (Int) -> Unit
) : ListAdapter<Note, NoteListAdapter.ViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    class ViewHolder(
        val onNoteClick: (Int) -> Unit,
        val onNoteDelete: (Int) -> Unit,
        val binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onNoteClick(adapterPosition)
            }
            binding.deleteImageView.setOnClickListener {
                onNoteDelete(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(onNoteClick, onNoteDelete, ItemNoteBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context

        holder.binding.titleTextView.text = currentList[position].title
        holder.binding.contentTextView.text = currentList[position].content
        holder.binding.layoutItem.backgroundTintList =
            ContextCompat.getColorStateList(
                context,
                when (currentList[position].colorCode) {
                    COLOR_CODE_RED -> R.color.note_red
                    COLOR_CODE_YELLOW -> R.color.note_yellow
                    COLOR_CODE_PURPLE -> R.color.note_purple
                    COLOR_CODE_BLUE -> R.color.note_blue
                    COLOR_CODE_PINK -> R.color.note_pink
                    else -> R.color.note_red
                }
            )
    }

    override fun getItemCount() = currentList.size
}