package com.far.notesapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.far.notesapp.feature_note.domain.util.NoteOrder
import com.far.notesapp.feature_note.domain.util.NoteOrder.*
import com.far.notesapp.feature_note.domain.util.OrderType.Ascending
import com.far.notesapp.feature_note.domain.util.OrderType.Descending

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = Date(Descending),
    onOrderChangeListener: (NoteOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Note order by:",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                isSelected = noteOrder is Title,
                onSelectListener = { onOrderChangeListener(Title(noteOrder.orderType)) })
            Spacer(modifier = Modifier.width(16.dp))
            DefaultRadioButton(
                text = "Date",
                isSelected = noteOrder is Date,
                onSelectListener = { onOrderChangeListener(Date(noteOrder.orderType)) })
            Spacer(modifier = Modifier.width(16.dp))
            DefaultRadioButton(
                text = "Color",
                isSelected = noteOrder is Color,
                onSelectListener = { onOrderChangeListener(Color(noteOrder.orderType)) })
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Order type by:",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                isSelected = noteOrder.orderType is Ascending,
                onSelectListener = { onOrderChangeListener(noteOrder.copy(Ascending)) })
            Spacer(modifier = Modifier.width(16.dp))
            DefaultRadioButton(
                text = "Descending",
                isSelected = noteOrder.orderType is Descending,
                onSelectListener = { onOrderChangeListener(noteOrder.copy(Descending)) })
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}