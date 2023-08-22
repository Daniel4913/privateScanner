package com.example.myocr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.myocr.presentation.HomeScreen
import com.example.myocr.ui.theme.MyOCRTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = MyViewModel()
        val billImageState = viewModel.imageState

        setContent {
            MyOCRTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(
                        chosenImage = billImageState.image.firstOrNull(),
                        onImageSelect = { viewModel.addImage(it) },
                        onMenuClicked = {},

                        )
                }
            }
        }
    }
}


