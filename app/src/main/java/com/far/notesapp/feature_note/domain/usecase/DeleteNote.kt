package com.far.notesapp.feature_note.domain.usecase

import com.far.notesapp.core.UseCase
import com.far.notesapp.feature_note.domain.exception.MissingParamsException
import com.far.notesapp.feature_note.domain.model.Note
import com.far.notesapp.feature_note.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) : UseCase<Unit, DeleteNote.Params>() {

    override fun run(params: Params?) = when (params) {
        null -> throw MissingParamsException()
        else -> repository.deleteNote(params.note)
    }

    data class Params(var note: Note)
}