package com.far.notesapp.feature_note.presentation.util

sealed class RoutesScreens(val route: String) {
    object NotesScreen: RoutesScreens("notes_screen")
    object AddNoteScreen: RoutesScreens("add_note_screen")
    object EditNoteScreen: RoutesScreens("edit_note_screen")
}