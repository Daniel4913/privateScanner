package com.example.myocr.mlkit

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import com.google.mlkit.vision.text.Text
import java.util.Arrays
import java.util.Collections.max
import java.util.Collections.min
import kotlin.math.max
import kotlin.math.min

class TextGraphic
constructor(
    private val overlay: GraphicOverlay?,
    private val text: Text,
    private val shouldGroupTextInBlocks: Boolean,
    private val showLanguageTag: Boolean,
    private val showConfidence: Boolean
) : GraphicOverlay.Graphic(overlay) {

    private val rectPaint: Paint = Paint()
    private val textPaint: Paint
    private val labelPaint: Paint

    private val clickableAreas = mutableMapOf<RectF, String>()


    init {
        rectPaint.color = MARKER_COLOR
        rectPaint.style = Paint.Style.STROKE
        rectPaint.strokeWidth = STROKE_WIDTH
        textPaint = Paint()
        textPaint.color = TEXT_COLOR
        textPaint.textSize = TEXT_SIZE
        labelPaint = Paint()
        labelPaint.color = MARKER_COLOR
        labelPaint.style = Paint.Style.FILL
        // Redraw the overlay, as this graphic has been added.
        postInvalidate()
        overlay?.setOnClickListener(GraphicOverlay.OnClickListener {
            Log.d(
                "KLIKED",
                "klikniety "
            )
        })
    }

    /** Draws the text block annotations for position, size, and raw value on the supplied canvas. */
    override fun draw(canvas: Canvas) {
        Log.d(TAG, "Text is: " + text.text)
        for (textBlock in text.textBlocks) { // Renders the text at the bottom of the box.
            Log.d(TAG, "TextBlock text is: " + textBlock.text)
            Log.d(TAG, "TextBlock boundingbox is: " + textBlock.boundingBox)
            Log.d(TAG, "TextBlock cornerpoint is: " + Arrays.toString(textBlock.cornerPoints))
            if (shouldGroupTextInBlocks) {

                drawText(
                    getFormattedText(
                        textBlock.text,
                        textBlock.recognizedLanguage,
                        confidence = null
                    ),
                    RectF(textBlock.boundingBox),
                    TEXT_SIZE * textBlock.lines.size + 2 * STROKE_WIDTH,
                    canvas
                )
            } else {
                for (line in textBlock.lines) {
                    Log.d(TAG, "Line text is: " + line.text)
                    Log.d(TAG, "Line boundingbox is: " + line.boundingBox)
                    Log.d(TAG, "Line cornerpoint is: " + Arrays.toString(line.cornerPoints))
                    Log.d(TAG, "Line confidence is: " + line.confidence)
                    Log.d(TAG, "Line angle is: " + line.angle)
                    // Draws the bounding box around the TextBlock.
                    val rect = RectF(line.boundingBox)
                    drawText(
                        getFormattedText(line.text, line.recognizedLanguage, line.confidence),
                        rect,
                        TEXT_SIZE + 2 * STROKE_WIDTH,
                        canvas
                    )

                    for (element in line.elements) {
                        Log.d(TAG, "Element text is: " + element.text)
                        Log.d(TAG, "Element boundingbox is: " + element.boundingBox)
                        Log.d(
                            TAG,
                            "Element cornerpoint is: " + Arrays.toString(element.cornerPoints)
                        )
                        Log.d(TAG, "Element language is: " + element.recognizedLanguage)
                        Log.d(TAG, "Element confidence is: " + element.confidence)
                        Log.d(TAG, "Element angle is: " + element.angle)
                        for (symbol in element.symbols) {
                            Log.d(TAG, "Symbol text is: " + symbol.text)
                            Log.d(TAG, "Symbol boundingbox is: " + symbol.boundingBox)
                            Log.d(
                                TAG,
                                "Symbol cornerpoint is: " + Arrays.toString(symbol.cornerPoints)
                            )
                            Log.d(TAG, "Symbol confidence is: " + symbol.confidence)
                            Log.d(TAG, "Symbol angle is: " + symbol.angle)
                        }
                    }
                }
            }
        }
    }

    private fun getFormattedText(text: String, languageTag: String, confidence: Float?): String {
        val res =
            if (showLanguageTag) String.format(
                TEXT_WITH_LANGUAGE_TAG_FORMAT,
                languageTag,
                text
            ) else text
        return if (showConfidence && confidence != null) String.format("%s (%.2f)", res, confidence)
        else res
    }

    private fun drawText(text: String, rect: RectF, textHeight: Float, canvas: Canvas) {
        text.forEach {
            overlay?.setOnClickListener(GraphicOverlay.OnClickListener {
                Log.d(
                    "KLIKED",
                    "klikniety ${it} "
                )
            })
        }
        // If the image is flipped, the left will be translated to right, and the right to left.
        val x0 = translateX(rect.left)
        val x1 = translateX(rect.right)
        rect.left = min(x0, x1)
        rect.right = max(x0, x1)
        rect.top = translateY(rect.top)
        rect.bottom = translateY(rect.bottom)
        canvas.drawRect(rect, rectPaint)
        val textWidth = textPaint.measureText(text)
        canvas.drawRect(
            rect.left - STROKE_WIDTH,
            rect.top - textHeight,
            rect.left + textWidth + 2 * STROKE_WIDTH,
            rect.top,
            labelPaint
        )
        // Renders the text at the bottom of the box.
        canvas.drawText(text, rect.left, rect.top - STROKE_WIDTH, textPaint)
        overlay?.setOnClickListener(GraphicOverlay.OnClickListener {
            Log.d(
                "KLIKED",
                "klikniety ${text} "
            )
        })
        clickableAreas[rect] = text
    }

    fun getClickedText(x: Float, y: Float): String? {
        for ((rect, text) in clickableAreas) {
            if (x >= rect.left && x <= rect.right && y >= rect.top && y <= rect.bottom) {
                return text
            }
        }
        return null
    }


    fun contains(x: Float, y: Float): Boolean {
        for (textBlock in text.textBlocks) {
            val rect = RectF(textBlock.boundingBox)
            val left = translateX(rect.left)
            val right = translateX(rect.right)
            val top = translateY(rect.top)
            val bottom = translateY(rect.bottom)
            if (x >= left && x <= right && y >= top && y <= bottom) {
                return true
            }
        }
        return false
    }


    companion object {
        private const val TAG = "TextGraphic"
        private const val TEXT_WITH_LANGUAGE_TAG_FORMAT = "%s:%s"
        private const val TEXT_COLOR = Color.BLACK
        private const val MARKER_COLOR = Color.WHITE
        private const val TEXT_SIZE = /*24.0f*/ 1f
        private const val STROKE_WIDTH = 4.0f/*1f */
    }
}
