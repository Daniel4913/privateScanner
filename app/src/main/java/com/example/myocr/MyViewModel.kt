package com.example.myocr

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.myocr.components.ImageData
import com.example.myocr.components.ImageState
import com.google.mlkit.vision.text.Text

class MyViewModel() : ViewModel() {

    val imageState = ImageState()

    fun addImage(image: Uri) {
        imageState.addImage(ImageData(imageUri = image, imageText = null))
    }

    fun addText(text: Text){
        imageState.addText(text = text)
    }
}