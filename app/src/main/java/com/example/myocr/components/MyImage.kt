package com.example.myocr.components

import android.net.Uri
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.google.mlkit.vision.text.Text

@Composable
fun MyImage(
    image: Uri,
    imageText: Text? = null
) {
    // todo make viewpager; ?how i'm using benefits of BoxWithConstraints here?
    BoxWithConstraints() {
        Row() {

        }
    }

}