package com.far.notesapp.di

import android.app.Application
import androidx.room.Room
import com.far.notesapp.feature_note.data.datasource.NoteDatabase
import com.far.notesapp.feature_note.data.repository.NoteRepositoryImpl
import com.far.notesapp.feature_note.domain.repository.NoteRepository
import com.far.notesapp.feature_note.domain.usecase.DeleteNote
import com.far.notesapp.feature_note.domain.usecase.GetNotes
import com.far.notesapp.feature_note.domain.usecase.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(application: Application): NoteDatabase {
        return Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(database: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(database.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
        )
    }
}