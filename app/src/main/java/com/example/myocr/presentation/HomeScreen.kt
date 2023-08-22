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
import com.example.myocr.components.GraphicManager
import com.example.myocr.components.ImageData
import com.example.myocr.components.TextRecognitionOverlay
import com.example.myocr.mlkit.BitmapUtils
import com.example.myocr.mlkit.GraphicOverlay
import com.example.myocr.mlkit.TextRecognitionProcessor
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.launch
import java.io.IOException

fun extractInformation(text: String): Map<String, String> {
    val regexMap = mapOf(
        "Nazwa produktu" to "Deska Sup \\d+ szt\\.",
        "Ilość" to "(\\d+\\.\\d{2})",
        "Cena" to "(\\d+\\.\\d{2})",
        "Suma" to "(\\d+\\.\\d{2})",
        "Data" to "(\\d{2}-\\d{2}-\\d{4})",
        "Godzina" to "(\\d{2}-\\d{2})",
        "Sklep" to "Hotel Tanzanit",
        "Adres" to "67-415 Kolsko"
    )

    val extractedData = mutableMapOf<String, String>()

    for ((key, pattern) in regexMap) {
        val regexResult = Regex(pattern).find(text)
        if (regexResult != null) {
            extractedData[key] = regexResult.groupValues.last()
        }
    }

    return extractedData
}

fun main() {
    val text = "\"GoLden Play\" Sp. Z 0.0.\n" +
            "Hotel Tanzanit\n" +
            "67-415 Kolsko\n" +
            "NIP: 8971010838\n" +
            "Jesi onka 57\n" +
            "PARAGON FISKALNY\n" +
            "Deska Sup 1 szt.*30.00\n" +
            "Sprzedaz opodatkowana A:\n" +
            "KHota PTUA 232\n" +
            "SUHA PTU\n" +
            "SUMA:\n" +
            "Karta\n" +
            "DO ZAPŁATY:\n" +
            "HO03476\n" +
            "ROZLICZENIE PŁATNOSCI\n" +
            "30.00A\n" +
            "30. 00\n" +
            "5.61\n" +
            "5.61\n" +
            "PLN 30.00\n" +
            "30.00\n" +
            "30.00\n" +
            "F3007 #1 JIstyna Krói 2 21-05-2023 13-46\n" +
            "F6785EATF1AACF221432E07A 1B445A50C3BF6040\n" +
            "P EAE 1901266009\n" +
            "3780"

    val extractedInfo = extractInformation(text)

    for ((key, value) in extractedInfo) {
        println("$key: $value")
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
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
    val context = LocalContext.current
    var showExtractedInfo by remember { mutableStateOf(false) }
    var extractedData by remember { mutableStateOf(emptyMap<String, String>()) }

    // tooltip - do wyjebania
    val tooltipState = remember { PlainTooltipState() }
    val scope = rememberCoroutineScope()

    // Coping
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                onImageSelect(uri)
            }
        }
    )

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
                    IconButton(
                        onClick = {
                            if (chosenImage != null) {
                                val image =
                                    imageFromPath(context = context, chosenImage.imageUri)
                                recognizeText(image!!)
                                extractedData = extractInformation(result)
                                // Ustawienie stanu, aby pokazać ekstrahowane informacje
                                showExtractedInfo = true
                                Log.d("extractedData", "$extractedData")
                            } else {
                                Log.d("Analyze failed", "choosenImage = $chosenImage")
                            }
                        }) {
                        Text(text = "Anal yze")
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
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (chosenImage != null) {
                    TextRecognitionOverlay(chosenImage = chosenImage)
                }
            }

//            val graphicManager = object : GraphicManager {
//                override fun clear() {
//                    TODO("Not yet implemented")
//                }
//
//                override fun add(graphic: GraphicOverlay.Graphic) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun remove(graphic: GraphicOverlay.Graphic) {
//                    TODO("Not yet implemented")
//                }
//            }
//            LazyColumn(
//                modifier = Modifier
//                    .wrapContentHeight()
//                    .padding(paddingValues = it)
//                    .navigationBarsPadding()
//                    .padding(top = padding.calculateTopPadding()),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                item {
//                    Column(
//                        modifier = Modifier
//                            .height(500.dp)
//                            .verticalScroll(scrollState)
//                            .padding(horizontal = 24.dp)
//                            .navigationBarsPadding()
//                            .padding(top = padding.calculateTopPadding()),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        padding = it
//                        if (showExtractedInfo) {
//                            ExtractedInformationFields(extractedData)
//                        }
//                        ExtractedInformationFields(extractedData = extractedData)
//                    }
//                }
//            }
        }
    )
}



