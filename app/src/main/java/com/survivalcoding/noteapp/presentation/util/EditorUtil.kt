package com.survivalcoding.noteapp.presentation.util

import android.widget.EditText
import com.survivalcoding.noteapp.R
import com.survivalcoding.noteapp.presentation.event.UserEvent

class EditorUtil {
    companion object {
        fun checkEditTextsNotEmpty(
            titleEditText: EditText,
            contentEditText: EditText
        ): UserEvent.ShowSnackBar? {
            val context = titleEditText.context

            return if (titleEditText.text.toString().isEmpty())
                UserEvent.ShowSnackBar(context.getString(R.string.message_empty_title))
            else if (contentEditText.text.toString().isEmpty())
                UserEvent.ShowSnackBar(context.getString(R.string.message_empty_content))
            else null
        }
    }
}