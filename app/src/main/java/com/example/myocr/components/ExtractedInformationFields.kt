package com.example.myocr.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester

@Composable
fun ExtractedInformationFields(extractedData: Map<String, String>) {
    var selectedField = if (extractedData.isNotEmpty()) {
        extractedData.keys.first()
    } else {
        // Handle the case when extractedData is empty
        ""
    }
    val focusRequesterMap = remember { mutableMapOf<String, FocusRequester>() }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        extractedData.forEach { (key, value) ->
            val focusRequester = remember { FocusRequester() }
            focusRequesterMap[key] = focusRequester
            Row(verticalAlignment = Alignment.CenterVertically) {
                ExtractedInformationPicker(
                    key = key,
                    value = value,
                    isSelected = key == selectedField,
                    focusRequester = focusRequester,
                    onClick = { selectedField = key }
                )
            }
        }
    }
    LaunchedEffect(selectedField) {
        focusRequesterMap[selectedField]?.requestFocus()
    }
}