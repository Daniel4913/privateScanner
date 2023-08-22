//package com.example.myocr.presentation
//
//import android.content.Context
//import android.net.Uri
//import android.util.Log
//import android.view.LayoutInflater
//import android.widget.DatePicker
//import android.widget.NumberPicker
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.PickVisualMediaRequest
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.focusable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.calculateEndPadding
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.requiredHeight
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.rounded.Add
//import androidx.compose.material.icons.rounded.Info
//import androidx.compose.material3.Button
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.Divider
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.PlainTooltipBox
//import androidx.compose.material3.PlainTooltipState
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Shapes
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.focus.focusRequester
//import androidx.compose.ui.focus.onFocusChanged
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.nestedscroll.nestedScroll
//import androidx.compose.ui.platform.ClipboardManager
//import androidx.compose.ui.platform.LocalClipboardManager
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.text.AnnotatedString
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.LayoutDirection
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import com.example.myocr.R
//import com.example.myocr.components.ImageData
//import com.google.mlkit.vision.common.InputImage
//import com.google.mlkit.vision.text.Text
//import com.google.mlkit.vision.text.TextRecognition
//import com.google.mlkit.vision.text.latin.TextRecognizerOptions
//import kotlinx.coroutines.launch
//import java.io.IOException
//import java.util.Calendar
//import java.util.Date
//
//fun extractInformation(text: String): Map<String, String> {
//    val regexMap = mapOf(
//        "Nazwa produktu" to "Deska Sup \\d+ szt\\.",
//        "Ilość" to "(\\d+\\.\\d{2})",
//        "Cena" to "(\\d+\\.\\d{2})",
//        "Suma" to "(\\d+\\.\\d{2})",
//        "Data" to "(\\d{2}-\\d{2}-\\d{4})",
//        "Godzina" to "(\\d{2}-\\d{2})",
//        "Sklep" to "Hotel Tanzanit",
//        "Adres" to "67-415 Kolsko"
//    )
//
//    val extractedData = mutableMapOf<String, String>()
//
//    for ((key, pattern) in regexMap) {
//        val regexResult = Regex(pattern).find(text)
//        if (regexResult != null) {
//            extractedData[key] = regexResult.groupValues.last()
//        }
//    }
//
//    return extractedData
//}
//
//fun main() {
//    val text = "\"GoLden Play\" Sp. Z 0.0.\n" +
//            "Hotel Tanzanit\n" +
//            "67-415 Kolsko\n" +
//            "NIP: 8971010838\n" +
//            "Jesi onka 57\n" +
//            "PARAGON FISKALNY\n" +
//            "Deska Sup 1 szt.*30.00\n" +
//            "Sprzedaz opodatkowana A:\n" +
//            "KHota PTUA 232\n" +
//            "SUHA PTU\n" +
//            "SUMA:\n" +
//            "Karta\n" +
//            "DO ZAPŁATY:\n" +
//            "HO03476\n" +
//            "ROZLICZENIE PŁATNOSCI\n" +
//            "30.00A\n" +
//            "30. 00\n" +
//            "5.61\n" +
//            "5.61\n" +
//            "PLN 30.00\n" +
//            "30.00\n" +
//            "30.00\n" +
//            "F3007 #1 JIstyna Krói 2 21-05-2023 13-46\n" +
//            "F6785EATF1AACF221432E07A 1B445A50C3BF6040\n" +
//            "P EAE 1901266009\n" +
//            "3780"
//
//    val extractedInfo = extractInformation(text)
//
//    for ((key, value) in extractedInfo) {
//        println("$key: $value")
//    }
//}
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeScreen(
//    chosenImage: ImageData?,
//    onImageSelect: (Uri) -> Unit,
//    onMenuClicked: () -> Unit,
//    recognitionResult: (Text) -> Unit,
//    recognizedText: () -> Text
//) {
//    var padding by remember { mutableStateOf(PaddingValues()) }
//    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
//    val scrollState = rememberScrollState()
//    var result by remember { mutableStateOf("") }
//    var analyzeSuccessful by remember { mutableStateOf(false) }
//
//
//    // TODO tooltip
//    val tooltipState = remember { PlainTooltipState() }
//    val scope = rememberCoroutineScope()
//
//    // Coping
//    val clipboardManager: ClipboardManager = LocalClipboardManager.current
//
//
//    val photoPicker = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.PickVisualMedia(),
//    ) { image ->
//        if (image != null) {
//            onImageSelect(image)
//
//        }
//
//    }
//
//    fun imageFromPath(context: Context, uri: Uri): InputImage? {
//        val image: InputImage
//        return try {
//            image = InputImage.fromFilePath(context, uri)
//            image
//        } catch (e: IOException) {
//            e.printStackTrace()
//            null
//        }
//    }
//
//
//    fun recognizeText(image: InputImage): Text {
//        var output: Text = Text("empty", emptyList<Text.TextBlock>())
//        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
//
//        recognizer.process(image)
//            .addOnSuccessListener { visionText ->
//                Log.d("recognizeText success result: ", visionText.text)
//                output = visionText
//                result = visionText.text
//                recognitionResult(output)
//                analyzeSuccessful = true
//            }
//            .addOnFailureListener { e ->
//                output = Text("e: $e. e.message: ${e.message}", emptyList<Text.TextBlock>())
//            }
//        return output
//    }
//
//    val context = LocalContext.current
//
//    Scaffold(
//        modifier = Modifier
//            .nestedScroll(scrollBehavior.nestedScrollConnection),
//        topBar = {
//            CenterAlignedTopAppBar(
//                scrollBehavior = scrollBehavior,
//                title = { Text(text = "Private Scanner") },
//                modifier = Modifier,
//                navigationIcon = {
//                    IconButton(onClick = onMenuClicked) {
//                        Icon(
//                            imageVector = Icons.Default.Menu,
//                            contentDescription = "Hamburger Menu Icon",
//                            tint = MaterialTheme.colorScheme.onSurface
//                        )
//                    }
//                },
//                actions = {
//                    PlainTooltipBox(
//                        modifier = Modifier,
//                        tooltip = { Text("Click recognized text row to copy this row to clipboard") },
//                        tooltipState = tooltipState
//                    ) {
//                        IconButton(
//                            onClick = { scope.launch { tooltipState.show() } },
//                            content = {
//                                Icon(
//                                    imageVector = Icons.Rounded.Info,
//                                    contentDescription = "Coping information",
//                                )
//                            }
//                        )
//                    }
//                },
//            )
//        },
//        floatingActionButton = {
//            FloatingActionButton(
//                modifier = Modifier
//                    .padding(end = padding.calculateEndPadding(LayoutDirection.Ltr)),
//                onClick = {
//                    photoPicker.launch(
//                        PickVisualMediaRequest(
//                            ActivityResultContracts.PickVisualMedia.ImageOnly
//                        )
//                    )
//                },
//            ) {
//                Icon(
//                    imageVector = Icons.Rounded.Add,
//                    contentDescription = "Add icon"
//                )
//            }
//        },
//        content = {
//            val textBlocks = recognizedText().textBlocks
//            var showExtractedInfo by remember { mutableStateOf(false) }
//            var extractedData by remember { mutableStateOf(emptyMap<String, String>()) }
//
//
//
//            LazyColumn(
//                modifier = Modifier.wrapContentHeight()
//            ) {
//                item {
//                    //row od gpt
//
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
//                        Text(
//                            text = "Here is your optional description:\n$chosenImage",
//                            style = TextStyle(fontSize = MaterialTheme.typography.titleSmall.fontSize)
//                        )
//                        AsyncImage(
//                            modifier = Modifier
//                                .clip(Shapes().medium)
//                                .size(300.dp),
//                            model = ImageRequest.Builder(LocalContext.current)
//                                .data(chosenImage?.imageUri)
//                                .crossfade(true)
//                                .build(), contentDescription = "Image"
//                        )
//                        Row(
//                            horizontalArrangement = Arrangement.Center
//                        ) {
//                            Spacer(Modifier.requiredHeight(30.dp))
////                            OutlinedButton(
////                                enabled = analyzeSuccessful,
////                                onClick = {
////                                    clipboardManager.setText(
////                                        annotatedString = AnnotatedString(
////                                            result
////                                        )
////                                    )
////                                    scope.launch { tooltipState.show() }
////                                }
////                            ) {
////                                Text("Copy all")
////                            }
//                            Button(
//                                onClick = {
//                                    if (chosenImage != null) {
//                                        val image =
//                                            imageFromPath(context = context, chosenImage.imageUri)
//                                        recognizeText(image!!)
//                                        extractedData = extractInformation(result)
//                                        // Ustawienie stanu, aby pokazać ekstrahowane informacje
//                                        showExtractedInfo = true
//                                        Log.d("extractedData", "$extractedData")
//
//                                    } else {
//                                        Log.d("Analyze failed", "choosenImage = $chosenImage")
//                                    }
//                                }) {
//                                Text(text = "Anal yze")
//                            }
//
//                        }
//
//
//                        Row(
//                            horizontalArrangement = Arrangement.Center
//                        ) {
//                            Spacer(Modifier.requiredHeight(30.dp))
//                            OutlinedButton(
//                                enabled = analyzeSuccessful,
//                                onClick = {
//                                    clipboardManager.setText(
//                                        annotatedString = AnnotatedString(
//                                            result
//                                        )
//                                    )
//                                    scope.launch { tooltipState.show() }
//                                }
//                            ) {
//                                Text("Copy all")
//                            }
////                            Button(
////                                onClick = {
////                                    if (chosenImage != null) {
////                                        val image =
////                                            imageFromPath(context = context, chosenImage.imageUri)
////                                        recognizeText(image!!)
////                                        val extractedData = extractInformation(result)
////                                        // Zastąp wydruk wyników extractedData
////                                        extractedData.forEach { (key, value) ->
////                                            println("$key: $value")
////                                        }
////                                    } else {
////                                        Log.d("Analyze failed", "choosenImage = $chosenImage")
////                                    }
////                                }) {
////                                Text(text = "Analyze")
////                            }
//                        }
//                        //row od gpt
//                    }
//
//                }
//
//                if (showExtractedInfo) {
//                    item {
//                        ExtractedInformationFields(extractedData)
//                    }
//                }
//
////                items(textBlocks) {
////                    Text(
////                        modifier = Modifier
////                            .fillMaxWidth()
////                            .clickable {
////                                clipboardManager.setText(
////                                    annotatedString = AnnotatedString(it.text)
////                                )
////                            },
////                        text = it.text
////                    )
////                    Divider()
////                }
//            }
//
//
//            ////
//            // todo
//        }
//    )
//}
//
//
//@Composable
//fun FourInputTextsScreen() {
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = Color.White
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentHeight()
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.Center
//            ) {
//
//            }
//        }
//    }
//}
//
////@Composable
////fun ExtractedInformationFields(extractedData: Map<String, String>) {
////    Column(
////        modifier = Modifier
////            .fillMaxWidth()
////            .padding(16.dp)
////    ) {
////        for ((key, value) in extractedData) {
////            OutlinedTextField(
////                value = "$key: \"$value\"",
////                onValueChange = { /* Not applicable for displaying values */ },
////                label = { Text("Input Text") },
////                modifier = Modifier
////                    .padding(vertical = 8.dp)
////            )
////        }
////    }
////}
//
////@Composable
////fun ExtractedInformationFields(extractedData: Map<String, String>) {
////    var selectedField by remember { mutableStateOf(extractedData.keys.first()) }
////    val focusRequesterMap = remember { mutableMapOf<String, FocusRequester>() }
////
////    Column(
////        modifier = Modifier.fillMaxSize()
////    ) {
////        extractedData.forEach { (key, value) ->
////            val focusRequester = remember { FocusRequester() }
////            focusRequesterMap[key] = focusRequester
////
////            if (key == selectedField) {
////                ExtractedInformationPicker(
////                    key = key,
////                    value = value,
////                    isFocused = true,
////                    focusRequester = focusRequester
////                )
////            } else {
////                ExtractedInformationPicker(
////                    key = key,
////                    value = value,
////                    isFocused = false,
////                    focusRequester = focusRequester
////                )
////            }
////        }
////    }
////
////    LaunchedEffect(selectedField) {
////        focusRequesterMap[selectedField]?.requestFocus()
////    }
////}
////
////@Composable
////fun ExtractedInformationPicker(
////    key: String,
////    value: String,
////    isFocused: Boolean,
////    focusRequester: FocusRequester
////) {
////    Row(
////        modifier = Modifier
////            .padding(16.dp)
////            .fillMaxWidth()
////            .background(
////                color = if (isFocused) Color.Gray else Color.White
////            )
////            .onFocusChanged { isFocused ->
////                if (isFocused) {
////                    selectedField = key
////                }
////            }
////            .focusRequester(focusRequester)
////            .focusable()
////            .clickable { selectedField = key }
////            .padding(8.dp),
////        verticalAlignment = Alignment.CenterVertically
////    ) {
////        Text(
////            text = key,
////            fontWeight = FontWeight.Bold,
////            modifier = Modifier.weight(1f)
////        )
////        Text(
////            text = value,
////            modifier = Modifier.padding(start = 16.dp)
////        )
////    }
////}
//
//@Composable
//fun DatePicker(
//) {
//    AndroidView(
//        modifier = Modifier
//            .width(60.dp)
//            .height(100.dp )
//            .background(color = Color.White),
//        factory = { context ->
//            NumberPicker(context).apply {
//                setOnValueChangedListener { numberPicker, i, i2 -> }
//                minValue = 0
//                maxValue = 50
//            }
//        }
//    )
////    AndroidView(
////        modifier = Modifier.fillMaxWidth(),
////        factory = { context ->
////            val view = LayoutInflater.from(context).inflate(R.layout.my_picker, null)
////            val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
////            val calendar = Calendar.getInstance() // show today by default
////            datePicker.init(
////                calendar.get(Calendar.YEAR),
////                calendar.get(Calendar.MONTH),
////                calendar.get(Calendar.DAY_OF_MONTH)
////            ) { _, year, monthOfYear, dayOfMonth ->
////                val date = Calendar.getInstance().apply {
////                    set(year, monthOfYear, dayOfMonth)
////                }.time
////                onSelectedDateUpdate(date)
////            }
////            datePicker
////        }
////    )
//}
//
//@Composable
//fun ExtractedInformationFields(extractedData: Map<String, String>) {
//    var selectedField = if (extractedData.isNotEmpty()) {
//        extractedData.keys.first()
//    } else {
//        // Handle the case when extractedData is empty
//        ""
//    }
//
////    var selectedField by remember { mutableStateOf(extractedData.keys.first()) }
//
////    var selectedField by remember { mutableStateOf(extractedData.keys.first()) }
//    val focusRequesterMap = remember { mutableMapOf<String, FocusRequester>() }
//
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        extractedData.forEach { (key, value) ->
//            val focusRequester = remember { FocusRequester() }
//            focusRequesterMap[key] = focusRequester
//            Row(verticalAlignment = Alignment.CenterVertically) {
//
//                ExtractedInformationPicker(
//                    key = key,
//                    value = value,
//                    isSelected = key == selectedField,
//                    focusRequester = focusRequester,
//                    onClick = { selectedField = key }
//
//                )
//
//            }
//        }
//    }
//
//    LaunchedEffect(selectedField) {
//        focusRequesterMap[selectedField]?.requestFocus()
//    }
//
//}
//
//@Composable
//fun ExtractedInformationPicker(
//    key: String,
//    value: String,
//    isSelected: Boolean,
//    focusRequester: FocusRequester,
//    onClick: () -> Unit
//) {
//    Row(
//        modifier = Modifier
//            .padding(16.dp)
////            .fillMaxWidth()
//            .background(
//                color = if (isSelected) Color.Gray else Color.White
//            )
//            .clickable { onClick() }
//            .focusRequester(focusRequester)
//            .focusable()
//            .padding(8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            text = key,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.weight(1f), color = Color.Black
//        )
//        Text(
//            text = value,
//            modifier = Modifier.padding(start = 16.dp), color = Color.Black
//        )
//        DatePicker()
//    }
//}
//
//
//
//
