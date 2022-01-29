package com.far.notesapp.feature_note.domain.usecase

import com.far.notesapp.core.UseCase
import com.far.notesapp.feature_note.domain.exception.EmptyParamException
import com.far.notesapp.feature_note.domain.exception.MissingParamsException
import com.far.notesapp.feature_note.domain.model.Note
import com.far.notesapp.feature_note.domain.repository.NoteRepository

class InsertNote(
    private val repository: NoteRepository
) : UseCase<Unit, InsertNote.Params>() {

    override fun run(params: Params?) = when {
        params == null -> throw MissingParamsException()
        params.note.title.isEmpty() -> throw EmptyParamException("title")
        params.note.content.isEmpty() -> throw EmptyParamException("content")
        else -> repository.insertNote(params.note)
    }

    data class Params(var note: Note)
}