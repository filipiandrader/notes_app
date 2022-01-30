package com.far.notesapp.feature_note.presentation.add_edit_note.event

sealed class UiEvent {
    data class ShowSnackbarEvent(val message: String, val action: String) : UiEvent()
    object SaveNoteEvent: UiEvent()
}
