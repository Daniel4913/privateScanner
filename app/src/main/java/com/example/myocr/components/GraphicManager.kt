package com.example.myocr.components

import com.example.myocr.mlkit.GraphicOverlay


interface GraphicManager {
    fun clear()
    fun add(graphic: GraphicOverlay.Graphic)
    fun remove(graphic: GraphicOverlay.Graphic)
}
