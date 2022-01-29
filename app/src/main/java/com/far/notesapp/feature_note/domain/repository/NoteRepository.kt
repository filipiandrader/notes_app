package com.far.notesapp.feature_note.domain.repository

import com.far.notesapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>
    fun getNoteById(id: Int): Flow<Note?>
    fun insertNote(note: Note): Flow<Unit>
    fun deleteNote(note: Note): Flow<Unit>
}