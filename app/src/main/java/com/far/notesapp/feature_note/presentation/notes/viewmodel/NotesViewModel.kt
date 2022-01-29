package com.far.notesapp.feature_note.presentation.notes.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.far.notesapp.core.Constants.DEFAULT_ERROR
import com.far.notesapp.feature_note.domain.model.Note
import com.far.notesapp.feature_note.domain.usecase.DeleteNote
import com.far.notesapp.feature_note.domain.usecase.GetNotes
import com.far.notesapp.feature_note.domain.usecase.InsertNote
import com.far.notesapp.feature_note.domain.usecase.NoteUseCases
import com.far.notesapp.feature_note.domain.util.NoteOrder
import com.far.notesapp.feature_note.domain.util.NoteOrder.Date
import com.far.notesapp.feature_note.domain.util.OrderType.Descending
import com.far.notesapp.feature_note.presentation.notes.event.NotesEvent
import com.far.notesapp.feature_note.presentation.notes.state.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _noteState = mutableStateOf(NotesState())
    val noteState: State<NotesState> = _noteState

    private var recentlyDeletedNote: Note? = null

    init {
        getNotes(Date(Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.OrderEvent -> getNotes(event.noteOrder)
            is NotesEvent.DeleteEvent -> deleteNote(event.note)
            is NotesEvent.UndoEvent -> undoNote()
            is NotesEvent.ToggleOrderSectionEvent -> {
                _noteState.value = _noteState.value.copy(
                    isOrderSectionVisible = !_noteState.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        if (_noteState.value.noteOrder::class == noteOrder::class &&
            _noteState.value.noteOrder.orderType == noteOrder.orderType
        ) {
            return
        }
        noteUseCases.getNotes(
            params = GetNotes.Params(noteOrder),
            onSuccess = { _noteState.value = NotesState(notes = it, noteOrder = noteOrder) },
            onError = { _noteState.value = NotesState(error = it.message ?: DEFAULT_ERROR) }
        )
    }

    private fun deleteNote(note: Note) {
        noteUseCases.deleteNote(
            params = DeleteNote.Params(note),
            onSuccess = { recentlyDeletedNote = note },
            onError = { _noteState.value = NotesState(error = it.message ?: DEFAULT_ERROR) }
        )
    }

    private fun undoNote() {
        recentlyDeletedNote?.let { note ->
            noteUseCases.insertNote(
                params = InsertNote.Params(note),
                onSuccess = { recentlyDeletedNote = null },
                onError = { _noteState.value = NotesState(error = it.message ?: DEFAULT_ERROR) }
            )
        }
    }
}