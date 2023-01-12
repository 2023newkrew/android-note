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

class NoteListAdapter(val onNoteDelete: (Note) -> Unit) : ListAdapter<Note, NoteListAdapter.ViewHolder>(diffUtil) {
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

    class ViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(ItemNoteBinding.bind(view))
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

        holder.binding.deleteImageView.setOnClickListener{
            println("[Before] listAdapter Size: $itemCount")
            onNoteDelete(currentList[position])
            println("[After] listAdapter Size: $itemCount")
        }
    }

    override fun getItemCount() = currentList.size
}