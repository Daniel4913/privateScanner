package com.example.myocr.presentation

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myocr.components.ImageData
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.launch
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    chosenImage: ImageData?,
    onImageSelect: (Uri) -> Unit,
    onMenuClicked: () -> Unit,
    recognitionResult: (Text) -> Unit,
    recognizedText: () -> Text
) {
    var padding by remember { mutableStateOf(PaddingValues()) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scrollState = rememberScrollState()
    var result by remember { mutableStateOf("") }
    var analyzeSuccessful by remember { mutableStateOf(false) }


    // TODO tooltip
    val tooltipState = remember { PlainTooltipState() }
    val scope = rememberCoroutineScope()

    // Coping
    val clipboardManager: ClipboardManager = LocalClipboardManager.current


    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { image ->
        if (image != null) {
            onImageSelect(image)
        }
    }

    fun imageFromPath(context: Context, uri: Uri): InputImage? {
        val image: InputImage
        return try {
            image = InputImage.fromFilePath(context, uri)
            image
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }


    fun recognizeText(image: InputImage): Text {
        var output: Text = Text("empty", emptyList<Text.TextBlock>())
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                Log.d("recognizeText success result: ", visionText.text)
                output = visionText
                result = visionText.text
                recognitionResult(output)
                analyzeSuccessful = true
            }
            .addOnFailureListener { e ->
                output = Text("e: $e. e.message: ${e.message}", emptyList<Text.TextBlock>())
            }
        return output
    }

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
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
                    photoPicker.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add icon"
                )
            }
        },
        content = {
            val textBlocks = recognizedText().textBlocks
            LazyColumn(
                modifier = Modifier.wrapContentHeight()
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .height(500.dp)
                            .verticalScroll(scrollState)
                            .padding(horizontal = 24.dp)
                            .navigationBarsPadding()
                            .padding(top = padding.calculateTopPadding()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        padding = it
                        Text(
                            text = "Here is your optional description:\n$chosenImage",
                            style = TextStyle(fontSize = MaterialTheme.typography.titleSmall.fontSize)
                        )
                        AsyncImage(
                            modifier = Modifier
                                .clip(Shapes().medium)
                                .size(300.dp),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(chosenImage?.imageUri)
                                .crossfade(true)
                                .build(), contentDescription = "Image"
                        )
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Spacer(Modifier.requiredHeight(30.dp))
                            OutlinedButton(
                                enabled = analyzeSuccessful,
                                onClick = {
                                    clipboardManager.setText(
                                        annotatedString = AnnotatedString(
                                            result
                                        )
                                    )
                                    scope.launch { tooltipState.show() }
                                }
                            ) {
                                Text("Copy all")
                            }
                            Button(
                                onClick = {
                                    if (chosenImage != null) {
                                        val image =
                                            imageFromPath(context = context, chosenImage.imageUri)
                                        recognizeText(image!!)
                                    } else {
                                        Log.d("Analyze failed", "choosenImage = $chosenImage")
                                    }
                                }) {
                                Text(text = "Anal yze")
                            }
                        }
                    }
                }

                items(textBlocks) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                clipboardManager.setText(
                                    annotatedString = AnnotatedString(it.text)
                                )
                            },
                        text = it.text
                    )
                    Divider()
                }
            }
        }
    )
}

