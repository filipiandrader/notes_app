package com.far.notesapp.feature_note.domain.usecase

data class NoteUseCases(
    val getNotes: GetNotes,
    val getNote: GetNote,
    val deleteNote: DeleteNote,
    val insertNote: InsertNote
)
