package com.example.myocr.components

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.mlkit.vision.text.Text

@Composable
fun rememberBillImageState(): ImageState {
    return remember { ImageState() }
}

class ImageState {
    val image = mutableStateListOf<ImageData>()
    val imageText = mutableStateOf<Text>(Text("empty", emptyList<Text.TextBlock>()))
    val imagesToDelete = mutableStateListOf<ImageData>()

    fun addImage(imageData: ImageData) {
        image.clear()
        image.add(imageData)
    }

    fun addText(text: Text) {
        imageText.value = text
    }

    fun removeImage(imageData: ImageData) {

    }
}

data class ImageData(
    val imageUri: Uri,
    val imageText: Text? = null
)
