package com.example.myocr.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtractedInformationPicker(
    onClick: () -> Unit,
    name: String,
    onNameChanged: (String) -> Unit,
    quantity: (String),
    onQuantityChanged: (String) -> Unit,
    price: String,
    onPriceChanged: (String) -> Unit,
    quantityTimesPrice: (String),
    onQuantityTimesPriceChanged: (String) -> Unit,
    nameFocused: (Boolean) -> Unit,
    quantityFocused: (Boolean) -> Unit,
    priceFocused: (Boolean) -> Unit,
    quantityTimesPriceFocused: (Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()
    var isNameFocused by remember { mutableStateOf(false) }
    var isQuantityFocused by remember { mutableStateOf(false) }
    var isPriceFocused by remember { mutableStateOf(false) }
    var isQuantityTimesPriceFocused by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                modifier = Modifier
                    .weight(3f)
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isNameFocused = it.hasFocus
                        nameFocused(it.hasFocus)
                    },
                value = name,
                onValueChange = onNameChanged,
                placeholder = { Text(text = "Product name") },
                colors = TextFieldDefaults.colors(
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                ),
                maxLines = 1,
                singleLine = true,
            )

        }
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            TextField(modifier = Modifier
                .weight(2f)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isQuantityFocused = it.hasFocus
                    quantityFocused(it.hasFocus)
                },
                value = quantity,
                onValueChange = onQuantityChanged,
                placeholder = { Text(text = "pcs") },
                colors = TextFieldDefaults.colors(
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        scope.launch {
                            scrollState.animateScrollTo(Int.MAX_VALUE)
                        }
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                maxLines = 1,
                singleLine = true)
            TextField(modifier = Modifier
                .weight(2f)
                .requiredHeight(56.dp)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isPriceFocused = it.hasFocus
                    priceFocused(it.hasFocus)
                },
                value = price,
                onValueChange = onPriceChanged,
                placeholder = {
                    Text(
                        text = "price",
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                },
                colors = TextFieldDefaults.colors(

                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        scope.launch {
                            scrollState.animateScrollTo(Int.MAX_VALUE)
                        }
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                maxLines = 1,
                singleLine = true)
            TextField(modifier = Modifier
                .weight(2f)
                .requiredHeight(56.dp)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isQuantityTimesPriceFocused = it.hasFocus
                    quantityTimesPriceFocused(it.hasFocus)
                },
                value = quantityTimesPrice,
                onValueChange = onQuantityTimesPriceChanged,
                placeholder = {
                    Text(text = "total", fontSize = MaterialTheme.typography.bodySmall.fontSize)
                },
                colors = TextFieldDefaults.colors(
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                ), keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        scope.launch {
                            scrollState.animateScrollTo(Int.MAX_VALUE)
                        }
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                maxLines = 1,
                singleLine = true)
            IconButton(modifier=Modifier.weight(1f),onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = "Apply item button")
            }
        }
        Row(
            modifier = Modifier
                .background(Color.White),
             horizontalArrangement = Arrangement.Center //TODO NIE DZIAUA
        ) {
            DatePicker()
            DatePicker()
            DatePicker()
        }
    }
}