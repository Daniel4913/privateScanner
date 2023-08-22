package com.example.myocr.components

import android.widget.EditText
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtractedInformationPicker(
    isSelected: Boolean,
    onClick: () -> Unit,
    name: String,
    onNameChanged: (String) -> Unit,
    quantity: (String),
    onQuantityChanged: (String) -> Unit,
    price: String,
    onPriceChanged: (String) -> Unit,
    quantityTimesPrice: (String),
    onQuantityTimesPriceChanged: (String) -> Unit,
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    Column {
        Row(
            modifier = Modifier
                //            .padding(8.dp)
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                modifier = Modifier.weight(3f),
                value = name,
                onValueChange = onNameChanged,
                placeholder = { Text(text = "Product name") },
                colors = TextFieldDefaults.colors(
                    //                focusedContainerColor = Color.Transparent,
                    //                unfocusedContainerColor = Color.Transparent,
                    //                disabledContainerColor = Color.Transparent,
                    //                focusedIndicatorColor = Color.Unspecified,
                    //                unfocusedIndicatorColor = Color.Unspecified,
                    //                disabledIndicatorColor = Color.Unspecified,
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
                singleLine = true)}
            Row {
                TextField(modifier = Modifier.weight(1f),
                    value = name,
                    onValueChange = onNameChanged,
                    placeholder = { Text(text = "pcs") },
                    colors = TextFieldDefaults.colors(
                        //                focusedContainerColor = Color.Transparent,
                        //                unfocusedContainerColor = Color.Transparent,
                        //                disabledContainerColor = Color.Transparent,
                        //                focusedIndicatorColor = Color.Unspecified,
                        //                unfocusedIndicatorColor = Color.Unspecified,
                        //                disabledIndicatorColor = Color.Unspecified,
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
                    .weight(1f)
                    .requiredHeight(56.dp),
                    value = name,
                    onValueChange = onNameChanged,
                    placeholder = {
                        Text(
                            text = "price",
                            fontSize = MaterialTheme.typography.bodySmall.fontSize
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        //                focusedContainerColor = Color.Transparent,
                        //                unfocusedContainerColor = Color.Transparent,
                        //                disabledContainerColor = Color.Transparent,
                        //                focusedIndicatorColor = Color.Unspecified,
                        //                unfocusedIndicatorColor = Color.Unspecified,
                        //                disabledIndicatorColor = Color.Unspecified,
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
                    .weight(1f)
                    .requiredHeight(56.dp),
                    value = name, onValueChange = onNameChanged,
                    placeholder = {
                        Text(text = "total", fontSize = MaterialTheme.typography.bodySmall.fontSize)
                    },
                    colors = TextFieldDefaults.colors(
                        //                focusedContainerColor = Color.Transparent,
                        //                unfocusedContainerColor = Color.Transparent,
                        //                disabledContainerColor = Color.Transparent,
                        //                focusedIndicatorColor = Color.Unspecified,
                        //                unfocusedIndicatorColor = Color.Unspecified,
                        //                disabledIndicatorColor = Color.Unspecified,
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
            }
        Row(
            modifier = Modifier
                //            .padding(8.dp)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DatePicker()
        }
    }
}