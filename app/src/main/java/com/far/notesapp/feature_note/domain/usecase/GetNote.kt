package com.far.notesapp.feature_note.domain.usecase

import com.far.notesapp.core.UseCase
import com.far.notesapp.feature_note.domain.exception.EmptyParamException
import com.far.notesapp.feature_note.domain.exception.MissingParamsException
import com.far.notesapp.feature_note.domain.model.Note
import com.far.notesapp.feature_note.domain.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) : UseCase<Note?, GetNote.Params>() {

    override fun run(params: Params?) = when {
        params == null -> throw MissingParamsException()
        params.id == -1 -> throw EmptyParamException("id")
        else -> repository.getNoteById(params.id)
    }

    data class Params(var id: Int)
}