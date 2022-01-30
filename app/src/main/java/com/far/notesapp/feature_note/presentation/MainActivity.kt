package com.far.notesapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.far.notesapp.core.Constants.EDIT_PARAMS
import com.far.notesapp.core.Constants.NOTE_COLOR
import com.far.notesapp.core.Constants.NOTE_ID
import com.far.notesapp.feature_note.presentation.add_edit_note.screen.AddEditNoteScreen
import com.far.notesapp.feature_note.presentation.notes.screen.NotesScreen
import com.far.notesapp.feature_note.presentation.util.RoutesScreens
import com.far.notesapp.ui.theme.NotesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = RoutesScreens.NotesScreen.route
                    ) {
                        composable(route = RoutesScreens.NotesScreen.route) {
                            NotesScreen(navController = navController)
                        }
                        composable(route = RoutesScreens.AddNoteScreen.route) {
                            AddEditNoteScreen(navController = navController, -1)
                        }
                        composable(
                            route = RoutesScreens.EditNoteScreen.route.plus(EDIT_PARAMS),
                            arguments = listOf(
                                navArgument(name = NOTE_ID) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(name = NOTE_COLOR) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val color = it.arguments?.getInt(NOTE_COLOR) ?: -1
                            AddEditNoteScreen(navController = navController, noteColor = color)
                        }
                    }
                }
            }
        }
    }
}