package com.example.myapp.portofolio.ui

import android.graphics.Paint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myapp.portofolio.model.MonthPortofolio
import com.example.myapp.portofolio.util.parseMonthPortofolio
import com.example.myapp.portofolio.util.readJsonFromAssets
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.sp
import java.util.Random

@Composable
fun DonutChart(monthPortofolio: MonthPortofolio, scale: Float, onScaleChange: (Float) -> Unit) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .transformable(
                state = rememberTransformableState { zoomChange, _, _ ->
                    onScaleChange(scale * zoomChange)
                }
            )
            .graphicsLayer(scaleX = scale, scaleY = scale)
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.width / 4 * scale // Mengurangi ukuran donut dari sebelumnya
        val strokeWidth = size.width / 1 * scale

        var startAngle = -90f
        var fontSize = 22.sp * scale // Perbesar ukuran teks
        val textSpacing = 8.dp.toPx() // Jarak antara label dan percentage

        // Calculate text alignment and text offset based on scale
        val textAlignment = Paint.Align.RIGHT // Align text to the right for text at the end of the chart
        val textYOffset = 10f.dp.toPx() + textSpacing // Add 10dp spacing between label and percentage

        // Draw the donut chart
        for ((index, data) in monthPortofolio.data.withIndex()) {
            val percentage = data.percentage.toFloat()
            val sweepAngle = percentage * 360f / 100f

            drawArc(
                color = data.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )

            val textRadius = radius + strokeWidth / 2
            val textAngle = startAngle + sweepAngle / 2

            // Calculate text position to be at the end of the chart
            val textX = centerX + (textRadius - fontSize.toPx()) * kotlin.math.cos(Math.toRadians(textAngle.toDouble())).toFloat()
            val textY = centerY + (textRadius - fontSize.toPx()) * kotlin.math.sin(Math.toRadians(textAngle.toDouble())).toFloat()

            // Draw the label and percentage text
            val label = data.label
            val percentageText = "${data.percentage}%"
            drawIntoCanvas {
                it.nativeCanvas.apply {
                    drawText(label, textX, textY, Paint().apply {
                        textSize = fontSize.toPx()
                        isFakeBoldText = true // Set the text to bold
                        textAlign = textAlignment
                    })

                    drawText(percentageText, textX, textY + textYOffset, Paint().apply {
                        textSize = fontSize.toPx()
                        isFakeBoldText = true // Set the text to bold
                        textAlign = textAlignment
                    })
                }
            }

            startAngle += sweepAngle
        }
    }
}
