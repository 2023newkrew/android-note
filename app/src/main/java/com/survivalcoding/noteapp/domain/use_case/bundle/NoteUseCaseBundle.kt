package com.survivalcoding.noteapp.domain.use_case.bundle

import com.survivalcoding.noteapp.domain.use_case.DeleteNoteUseCase
import com.survivalcoding.noteapp.domain.use_case.GetNotesUseCase
import com.survivalcoding.noteapp.domain.use_case.InsertNoteUseCase
import com.survivalcoding.noteapp.domain.use_case.UpdateNoteUseCase

data class NoteUseCaseBundle(
    val getNotesUseCase: GetNotesUseCase,
    val insertNoteUseCase: InsertNoteUseCase,
    val deleteNotesUseCase: DeleteNoteUseCase,
    val updateNotesUseCase: UpdateNoteUseCase
)