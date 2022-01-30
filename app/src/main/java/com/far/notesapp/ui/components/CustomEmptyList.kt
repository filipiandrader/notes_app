package com.far.notesapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.far.notesapp.R

@Composable
fun CustomEmptyList() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = R.drawable.ic_emoticon_sad),
            contentDescription = "emoticon sad",
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Your list is empty",
                style = MaterialTheme.typography.h3
            )
        }
    }
}