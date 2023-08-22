package com.example.myocr.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ExtractedInformationPicker(
    key: String,
    value: String,
    isSelected: Boolean,
    focusRequester: FocusRequester,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
//            .fillMaxWidth()
            .background(
                color = if (isSelected) Color.Gray else Color.White
            )
            .clickable { onClick() }
            .focusRequester(focusRequester)
            .focusable()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = key,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f), color = Color.Black
        )
        Text(
            text = value,
            modifier = Modifier.padding(start = 16.dp), color = Color.Black
        )
        DatePicker()
    }
}