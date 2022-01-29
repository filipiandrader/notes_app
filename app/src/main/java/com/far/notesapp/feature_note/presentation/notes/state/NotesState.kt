package com.far.notesapp.feature_note.presentation.notes.state

import com.far.notesapp.feature_note.domain.model.Note
import com.far.notesapp.feature_note.domain.util.NoteOrder
import com.far.notesapp.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val error: String = ""
)