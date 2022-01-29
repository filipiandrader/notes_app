package com.far.notesapp.feature_note.presentation.add_edit_note.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.far.notesapp.core.Constants
import com.far.notesapp.feature_note.domain.model.Note
import com.far.notesapp.feature_note.domain.usecase.GetNote
import com.far.notesapp.feature_note.domain.usecase.InsertNote
import com.far.notesapp.feature_note.domain.usecase.NoteUseCases
import com.far.notesapp.feature_note.presentation.add_edit_note.event.AddEditNoteEvent
import com.far.notesapp.feature_note.presentation.add_edit_note.event.AddEditNoteEvent.*
import com.far.notesapp.feature_note.presentation.add_edit_note.event.UiEvent
import com.far.notesapp.feature_note.presentation.add_edit_note.state.NoteTextFieldState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitleState = mutableStateOf(NoteTextFieldState(hint = "Enter title..."))
    val noteTitleState: State<NoteTextFieldState> = _noteTitleState

    private val _noteContentState =
        mutableStateOf(NoteTextFieldState(hint = "Enter some content..."))
    val noteContentState: State<NoteTextFieldState> = _noteContentState

    private val _noteColorState = mutableStateOf(Note.noteColors.random().toArgb())
    val noteColorState: State<Int> = _noteColorState

    private val _uiEnventFlow = MutableSharedFlow<UiEvent>()
    val uiEventFlow = _uiEnventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                getNote(noteId)
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is EnteredTitleEvent -> {
                _noteTitleState.value = _noteTitleState.value.copy(text = event.value)
            }
            is ChangeTitleFocusEvent -> {
                _noteTitleState.value = _noteTitleState.value.copy(
                    isHintVisible = !event.focusState.isFocused && _noteTitleState.value.text.isEmpty()
                )
            }
            is EnteredContentEvent -> {
                _noteContentState.value = _noteContentState.value.copy(text = event.value)
            }
            is ChangeContentFocusEvent -> {
                _noteContentState.value = _noteContentState.value.copy(
                    isHintVisible = !event.focusState.isFocused && _noteContentState.value.text.isEmpty()
                )
            }
            is ChangeColorEvent -> _noteColorState.value = event.color
            is SaveNoteEvent -> saveNote(buildNote())
        }
    }

    private fun saveNote(note: Note) {
        noteUseCases.insertNote(
            params = InsertNote.Params(note),
            onSuccess = { viewModelScope.launch { _uiEnventFlow.emit(UiEvent.SaveNoteEvent) } },
            onError = { showError(it.message) }
        )
    }

    private fun buildNote() = Note(
        id = currentNoteId,
        title = _noteTitleState.value.text,
        content = _noteTitleState.value.text,
        timestamp = System.currentTimeMillis(),
        color = _noteColorState.value
    )

    private fun getNote(noteId: Int) {
        noteUseCases.getNote(
            params = GetNote.Params(noteId),
            onSuccess = {
                it?.let { note ->
                    currentNoteId = note.id
                    _noteTitleState.value = _noteTitleState.value.copy(
                        text = note.title,
                        isHintVisible = false
                    )
                    _noteContentState.value = _noteContentState.value.copy(
                        text = note.content,
                        isHintVisible = false
                    )
                    _noteColorState.value = note.color
                }
            },
            onError = { showError(it.message) }
        )
    }

    private fun showError(error: String?) {
        viewModelScope.launch {
            _uiEnventFlow.emit(UiEvent.ShowSnackbarEvent(error ?: Constants.DEFAULT_ERROR))
        }
    }
}