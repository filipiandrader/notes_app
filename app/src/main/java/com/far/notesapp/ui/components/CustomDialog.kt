package com.far.notesapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.far.notesapp.ui.theme.PrimaryDark

@Composable
fun CustomDialog(
    onEvent: () -> Unit,
    title: String,
    textContent: String = "",
    positiveText: String = "YES",
    negativeText: String = "NO",
    onNegativeClick: () -> Unit,
) {
    AlertDialog(
        backgroundColor = PrimaryDark,
        onDismissRequest = { onNegativeClick() },
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
        },
        text = {
            Text(
                text = textContent,
                style = MaterialTheme.typography.body2,
                color = Color.White
            )
        },
        buttons = {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = { onNegativeClick() }) {
                    Text(text = negativeText)
                }
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(
                    onClick = {
                        onEvent()
                        onNegativeClick()
                    }
                ) {
                    Text(text = positiveText)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    )
}