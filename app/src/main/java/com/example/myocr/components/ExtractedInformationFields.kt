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
fun ExtractedInformationFields() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ExtractedInformationPicker(
                isSelected = false,
                onClick = { },
                name = "",
                onNameChanged = {},
                quantity = "",
                onQuantityChanged = {},
                price = "",
                onPriceChanged = {},
                quantityTimesPrice = "",
                onQuantityTimesPriceChanged = {},

                )
        }
    }
}

