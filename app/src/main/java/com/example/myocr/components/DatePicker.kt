package com.example.myocr.components

import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun DatePicker(
) {
    AndroidView(
        modifier = Modifier
            .width(60.dp)
            .height(100.dp)
            .background(color = Color.White),
        factory = { context ->
            NumberPicker(context).apply {
                setOnValueChangedListener { numberPicker, i, i2 -> }
                minValue = 0
                maxValue = 50
            }
        }
    )
//    AndroidView(
//        modifier = Modifier.fillMaxWidth(),
//        factory = { context ->
//            val view = LayoutInflater.from(context).inflate(R.layout.my_picker, null)
//            val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
//            val calendar = Calendar.getInstance() // show today by default
//            datePicker.init(
//                calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH)
//            ) { _, year, monthOfYear, dayOfMonth ->
//                val date = Calendar.getInstance().apply {
//                    set(year, monthOfYear, dayOfMonth)
//                }.time
//                onSelectedDateUpdate(date)
//            }
//            datePicker
//        }
//    )
}