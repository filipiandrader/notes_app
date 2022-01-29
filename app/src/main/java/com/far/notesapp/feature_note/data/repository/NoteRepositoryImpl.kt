package com.far.notesapp.feature_note.data.repository

import com.far.notesapp.feature_note.data.datasource.NoteDao
import com.far.notesapp.feature_note.domain.model.Note
import com.far.notesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.flow

class NoteRepositoryImpl(private val dao: NoteDao) : NoteRepository {

    override fun getNotes() = dao.getNotes()

    override fun getNoteById(id: Int) = flow { emit(dao.getNoteById(id)) }

    override fun insertNote(note: Note) = flow { emit(dao.insertNote(note)) }

    override fun deleteNote(note: Note) = flow { emit(dao.deleteNote(note)) }
}