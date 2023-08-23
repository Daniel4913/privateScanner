package com.example.myocr.presentation

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.StackView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.PlainTooltipState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myocr.components.ExtractedInformationFields
import com.example.myocr.components.ExtractedInformationPicker
import com.example.myocr.components.GraphicManager
import com.example.myocr.components.ImageData
import com.example.myocr.components.TextRecognitionOverlay
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    chosenImage: ImageData?,
    onImageSelect: (Uri) -> Unit,
    onMenuClicked: () -> Unit,
) {
    var padding by remember { mutableStateOf(PaddingValues()) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
//    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scrollState = rememberScrollState()

    // tooltip - do wyjebania
    val tooltipState = remember { PlainTooltipState() }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var quantityTimesPrice by remember { mutableStateOf("") }

    //states for extract picker
    var isNameFieldFocused by remember { mutableStateOf(false) }
    var isQuantityFieldFocused by remember { mutableStateOf(false) }
    var isPriceFieldFocused by remember { mutableStateOf(false) }
    var isQuantityTimesPriceFieldFocused by remember { mutableStateOf(false) }

    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                onImageSelect(uri)
            }
        }
    )
    Scaffold(
//        modifier = Modifier
//            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
//                scrollBehavior = scrollBehavior,
                title = { Text(text = "Private Scanner") },
                modifier = Modifier,
                navigationIcon = {
                    IconButton(onClick = onMenuClicked) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Hamburger Menu Icon",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    PlainTooltipBox(
                        modifier = Modifier,
                        tooltip = { Text("Click recognized text row to copy this row to clipboard") },
                        tooltipState = tooltipState
                    ) {
                        IconButton(
                            onClick = { scope.launch { tooltipState.show() } },
                            content = {
                                Icon(
                                    imageVector = Icons.Rounded.Info,
                                    contentDescription = "Coping information",
                                )
                            }
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(end = padding.calculateEndPadding(LayoutDirection.Ltr)),
                onClick = {
                    filePicker.launch("image/*")
                },
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add icon"
                )
            }
        },
        content = {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = it.calculateTopPadding())
                        .height(300.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    if (chosenImage != null) {
                        TextRecognitionOverlay(
                            chosenImage = chosenImage,
                            clickedText = { clickedText ->
                                when {
                                    isNameFieldFocused -> name = clickedText
                                    isQuantityFieldFocused -> quantity = clickedText
                                    isPriceFieldFocused -> price = clickedText
                                    isQuantityTimesPriceFieldFocused -> quantityTimesPrice =
                                        clickedText
                                }

                            })
                    } else {

                    }
                }

                ExtractedInformationPicker(
                    onClick = { /*TODO*/ },
                    name = name,
                    onNameChanged = {},
                    quantity = quantity,
                    onQuantityChanged = {},
                    price = price,
                    onPriceChanged = {},
                    quantityTimesPrice = quantityTimesPrice,
                    onQuantityTimesPriceChanged = {},
                    nameFocused = { isFocused -> isNameFieldFocused = isFocused },
                    quantityFocused = { isFocused -> isQuantityFieldFocused = isFocused },
                    priceFocused = { isFocused -> isPriceFieldFocused = isFocused },
                    quantityTimesPriceFocused = { isFocused ->
                        isQuantityTimesPriceFieldFocused = isFocused
                    }
                )
//                ExtractedInformationFields(name,
//                    quantity = "mnesarchum",
//                    price = "quidam",
//                    quantityTimesPrice = "omittam",
//                    nameFocused = false,
//                    quantityFocused = false,
//                    priceFocused = false,
//                    quantityTimesPriceFocused = false,
//                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val itemsCount = 50
                    items(itemsCount) { index ->
                        Text(
                            text = "Element $index",
                            modifier = Modifier.padding(8.dp),
                            color = Color.White
                        )
                    }
                }
            }
        }
    )
}


