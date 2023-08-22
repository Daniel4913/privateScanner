package com.example.myocr.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.myocr.mlkit.BitmapUtils
import com.example.myocr.mlkit.GraphicOverlay
import com.example.myocr.mlkit.TextRecognitionProcessor
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@Composable
fun TextRecognitionOverlay(chosenImage: ImageData) {
    val context = LocalContext.current
    val imageProcessor =
        TextRecognitionProcessor(context, TextRecognizerOptions.Builder().build())
    val graphicOverlay = remember { GraphicOverlay(context) }
    val imageBitmap = remember(chosenImage.imageUri) {
        BitmapUtils.getBitmapFromContentUri(context.contentResolver, chosenImage.imageUri)
    }

    Box(
        modifier = Modifier
            .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        imageBitmap?.let { bitmap ->
            val imageModifier = Modifier
                .fillMaxWidth()
                .aspectRatio(bitmap.width.toFloat() / bitmap.height)

            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Image",
                modifier = imageModifier,
                contentScale = ContentScale.FillWidth
            )

            AndroidView(
                modifier = imageModifier,
                factory = { graphicOverlay }
            ) { view ->
                view.layoutParams.width = bitmap.width
                view.layoutParams.height = bitmap.height
                view.invalidate()
                graphicOverlay.setImageSourceInfo(bitmap.width, bitmap.height, false)
                imageProcessor.processBitmap(bitmap, graphicOverlay)
            }

            DisposableEffect(Unit) {
                graphicOverlay.clear()
                graphicOverlay.setImageSourceInfo(bitmap.width, bitmap.height, false)
                imageProcessor.processBitmap(bitmap, graphicOverlay)
                onDispose { imageProcessor.stop() }
            }
        }
    }
}

//@Composable
//fun TextRecognitionOverlay(chosenImage: ImageData) {
//    val context = LocalContext.current
//    val imageProcessor =
//        TextRecognitionProcessor(context, TextRecognizerOptions.Builder().build())
//    val graphicOverlay = remember { GraphicOverlay(context) }
//    val imageBitmap = remember(chosenImage.imageUri) {
//        BitmapUtils.getBitmapFromContentUri(context.contentResolver, chosenImage.imageUri)
//    }
//    var overlayWidth by remember { mutableStateOf(0) }
//    var overlayHeight by remember { mutableStateOf(0) }
//
//    Box(
//        modifier = Modifier
//            .background(Color.Blue),
//        contentAlignment = Alignment.Center
//    ) {
//        imageBitmap?.let { bitmap ->
//
//            AsyncImage(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .onGloballyPositioned { layoutCoordinates ->
//                        val scaleFactor = layoutCoordinates.size.width.toFloat() / bitmap.width
//                        overlayWidth = layoutCoordinates.size.width
//                        overlayHeight = (bitmap.height * scaleFactor).toInt()
//                        Log.d(
//                            "myLog AsyncImage:",
//                            "width: ${layoutCoordinates.size.width} height:${layoutCoordinates.size.height}"
//                        )
//                        Log.d(
//                            "myLog bitmap:",
//                            "width: ${bitmap.width} height:${bitmap.height}"
//                        )
//                    },
//                contentScale = ContentScale.FillWidth,
//                model = bitmap,
//                contentDescription = "Image",
//            )
//            AndroidView(
//                modifier = Modifier
//                    .width(overlayWidth.dp)
//                    .height(overlayHeight.dp),
//                factory = { graphicOverlay }
//            ) { view ->
//                // Apply the same transformations to the overlay as the image
//                view.layoutParams.width = overlayWidth
//                view.layoutParams.height = overlayHeight
//                view.invalidate()
//                graphicOverlay.setImageSourceInfo(bitmap.width, bitmap.height, false)
//                imageProcessor.processBitmap(bitmap, graphicOverlay)
//
//                Log.d(
//                    "myLog Overlay:",
//                    "width: ${view.layoutParams.width} height:${view.layoutParams.height}"
//                )
//            }
//
//            DisposableEffect(Unit) {
//                graphicOverlay.clear()
//                graphicOverlay.setImageSourceInfo(bitmap.width, bitmap.height, false)
//                imageProcessor.processBitmap(bitmap, graphicOverlay)
//                onDispose { imageProcessor.stop() }
//            }
//        }
//    }
//}