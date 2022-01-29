package com.far.notesapp.feature_note.presentation.add_edit_note.event

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class EnteredTitleEvent(val value: String) : AddEditNoteEvent()
    data class ChangeTitleFocusEvent(val focusState: FocusState) : AddEditNoteEvent()
    data class EnteredContentEvent(val value: String) : AddEditNoteEvent()
    data class ChangeContentFocusEvent(val focusState: FocusState) : AddEditNoteEvent()
    data class ChangeColorEvent(val color: Int): AddEditNoteEvent()
    object SaveNoteEvent: AddEditNoteEvent()
}
