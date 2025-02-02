package com.returdev.tictactoe.ui.screen.game

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


/**
 * Draws a circular shape on the canvas based on the given progress and color.
 * @receiver DrawScope The drawing context to render the "O" shape.
 * @param progress A value between `0f` and `1f` indicating the drawing progress of the "O".
 *                 - At `0f`, no part of the "O" is drawn.
 *                 - At `1f`, the full circular "O" is drawn.
 * @param color The color used to draw the "O".
 */
private fun DrawScope.drawO(
    progress: Float,
    color: Color
) {
    drawArc(
        color = color,
        startAngle = -90f,
        sweepAngle = -360f * progress,
        useCenter = false,
        style = Stroke(
            width = this.size.width / 3,
            cap = StrokeCap.Round
        )
    )
}


/**
 * Draws an "X" shape on the canvas based on the given progress and color.
 *
 * @receiver DrawScope The drawing context to render the "X" shape.
 * @param progress A value between `0f` and `1f` indicating the drawing progress of the "X".
 *                 - At `0f`, no part of the "X" is drawn.
 *                 - At `1f`, the full "X" is drawn.
 * @param color The color used to draw the "X".
 */
private fun DrawScope.drawX(
    progress: Float,
    color: Color
) {
    val width = size.width
    val height = size.height
    val style = Stroke(
        width = width / 3,
        cap = StrokeCap.Round
    )

    // Calculate the progress of the first and second lines
    val firstLineProgress = (progress * 2).coerceAtMost(1f)
    val secondLineProgress = ((progress - 0.5f).coerceAtLeast(0f) * 2)

    // Define the path for the first diagonal line
    val firstLine = Path().apply {
        moveTo(0f, 0f)
        lineTo(width * firstLineProgress, height * firstLineProgress)
    }

    // Define the path for the second diagonal line
    val secondLine = Path().apply {
        moveTo(width, 0f)
        lineTo(width * (1 - secondLineProgress), height * secondLineProgress)
    }

    // Draw the first line of the "X"
    drawPath(
        path = firstLine,
        color = color,
        style = style
    )

    // Draw the second line of the "X" only if it has progress
    if (secondLineProgress != 0f) {
        drawPath(
            path = secondLine,
            color = color,
            style = style
        )
    }
}


@Composable
@Preview
private fun GameScreenPreview(){

    val isVisible = remember { mutableStateOf(false) };

    val animatedFloat = animateFloatAsState(targetValue = if (isVisible.value) 1f else 0f, animationSpec = tween(durationMillis = 1000, easing = LinearEasing ))

    Surface {

        Column(modifier = Modifier.fillMaxSize()) {

            Button(onClick = {isVisible.value = true}) {


            }

            if (isVisible.value){
                Canvas(
                    modifier = Modifier.size(50.dp).padding(10.dp)
                ){
//                    drawX(animatedFloat.value, Color.Red)
                    drawO(animatedFloat.value, Color.Red)
                }
            }


        }

    }

}