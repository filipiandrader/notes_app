package com.far.notesapp.feature_note.presentation.notes.screen

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.far.notesapp.core.Constants.NOTE_COLOR_PARAM
import com.far.notesapp.core.Constants.NOTE_ID_PARAM
import com.far.notesapp.feature_note.presentation.add_edit_note.event.UiEvent
import com.far.notesapp.feature_note.presentation.notes.components.NoteItem
import com.far.notesapp.feature_note.presentation.notes.components.OrderSection
import com.far.notesapp.feature_note.presentation.notes.event.NotesEvent
import com.far.notesapp.feature_note.presentation.notes.viewmodel.NotesViewModel
import com.far.notesapp.feature_note.presentation.util.RoutesScreens
import com.far.notesapp.ui.components.CustomEmptyList
import com.far.notesapp.ui.theme.PrimaryDark
import kotlinx.coroutines.flow.collect

@ExperimentalAnimationApi
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.noteState.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEventChannel.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbarEvent -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(NotesEvent.UndoEvent)
                    }
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(RoutesScreens.AddNoteScreen.route) },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                backgroundColor = PrimaryDark,
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val icon = when (state.isOrderSectionVisible) {
                            true -> Icons.Default.Close
                            false -> Icons.Default.Sort
                        }
                        val contentDescription = when (state.isOrderSectionVisible) {
                            true -> "Close order section"
                            false -> "Open order section"
                        }
                        Text(
                            text = "Your notes"
                        )
                        IconButton(
                            onClick = { viewModel.onEvent(NotesEvent.ToggleOrderSectionEvent) }
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = contentDescription
                            )
                        }
                    }
                }
            )
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                    noteOrder = state.noteOrder,
                    onOrderChangeListener = { viewModel.onEvent(NotesEvent.OrderEvent(it)) })
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    RoutesScreens.EditNoteScreen.route
                                        .plus("$NOTE_ID_PARAM${note.id}")
                                        .plus("$NOTE_COLOR_PARAM${note.color}")
                                )
                            },
                        onDeleteClickListener = viewModel::onEvent
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    if (state.notes.isEmpty()) {
        CustomEmptyList()
    }
}