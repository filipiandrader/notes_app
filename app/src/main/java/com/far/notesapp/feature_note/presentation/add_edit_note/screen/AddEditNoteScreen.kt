package com.far.notesapp.feature_note.presentation.add_edit_note.screen

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.far.notesapp.feature_note.domain.model.Note
import com.far.notesapp.feature_note.presentation.add_edit_note.components.TransparentHintTextField
import com.far.notesapp.feature_note.presentation.add_edit_note.event.AddEditNoteEvent
import com.far.notesapp.feature_note.presentation.add_edit_note.event.UiEvent
import com.far.notesapp.feature_note.presentation.add_edit_note.viewmodel.AddEditNoteViewModel
import com.far.notesapp.ui.theme.PrimaryDark
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitleState.value
    val contentState = viewModel.noteContentState.value
    val scaffoldState = rememberScaffoldState()
    val noteBackgroundAnimatable = remember {
        Animatable(Color(if (noteColor != -1) noteColor else viewModel.noteColorState.value))
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbarEvent -> scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                is UiEvent.SaveNoteEvent -> navController.popBackStack()
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNoteEvent)
                },
                backgroundColor = MaterialTheme.colors.background
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
        ) {
            TopAppBar(
                backgroundColor = PrimaryDark,
                title = {
                    Text(
                        text = viewModel.toolbarTitle
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "back-button")
                    }
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColorState.value == colorInt) Color.Black else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditNoteEvent.ChangeColorEvent(colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChangeListener = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitleEvent(it))
                },
                onFocusChangeListener = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocusEvent(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChangeListener = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredContentEvent(it))
                },
                onFocusChangeListener = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocusEvent(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}