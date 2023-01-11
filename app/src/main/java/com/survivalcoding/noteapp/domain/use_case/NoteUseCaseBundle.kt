package com.survivalcoding.noteapp.domain.use_case

data class NoteUseCaseBundle(
    val getNotesUseCase: GetNotesUseCase,
    val insertNoteUseCase: InsertNoteUseCase,
    val deleteNotesUseCase: DeleteNoteUseCase,
    val updateNotesUseCase: UpdateNoteUseCase
)