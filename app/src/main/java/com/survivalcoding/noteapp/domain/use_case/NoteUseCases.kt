package com.survivalcoding.noteapp.domain.use_case

class NoteUseCases(
    val getNotes: GetNotesUseCase,
    val deleteNote: DeleteNoteUseCase,
    val addNote: AddNoteUseCase,
)