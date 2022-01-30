package com.far.notesapp.feature_note.domain.usecase

import com.far.notesapp.core.UseCase
import com.far.notesapp.feature_note.domain.exception.MissingParamsException
import com.far.notesapp.feature_note.domain.model.Note
import com.far.notesapp.feature_note.domain.repository.NoteRepository
import com.far.notesapp.feature_note.domain.util.NoteOrder
import com.far.notesapp.feature_note.domain.util.NoteOrder.*
import com.far.notesapp.feature_note.domain.util.OrderType.Ascending
import com.far.notesapp.feature_note.domain.util.OrderType.Descending
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) : UseCase<List<Note>, GetNotes.Params>() {

    override fun run(params: Params?) = when (params) {
        null -> throw MissingParamsException()
        else -> repository.getNotes().map { notes ->
            when (params.noteOrder.orderType) {
                is Ascending -> {
                    when (params.noteOrder) {
                        is Title -> notes.sortedBy { it.title.lowercase() }
                        is Date -> notes.sortedBy { it.timestamp }
                        is Color -> notes.sortedBy { it.color }
                    }
                }
                is Descending -> {
                    when (params.noteOrder) {
                        is Title -> notes.sortedByDescending { it.title.lowercase() }
                        is Date -> notes.sortedByDescending { it.timestamp }
                        is Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }

    data class Params(var noteOrder: NoteOrder = Date(Descending))
}