package com.far.notesapp.feature_note.presentation.notes.event

import com.far.notesapp.feature_note.domain.model.Note
import com.far.notesapp.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class OrderEvent(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteEvent(val note: Note): NotesEvent()
    object UndoEvent: NotesEvent()
    object ToggleOrderSectionEvent: NotesEvent()
}
