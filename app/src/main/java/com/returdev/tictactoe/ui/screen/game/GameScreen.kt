package com.returdev.tictactoe.ui.screen.game

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.returdev.tictactoe.ui.screen.game.model.PlayerSymbol
import kotlin.random.Random


/**
 * Composable function representing a single cell on a game board.
 *
 * @param modifier Modifier for customizing the appearance and layout of the cell.
 * @param cellValue The current value of the cell, which can be either [PlayerSymbol.X], [PlayerSymbol.O], or null.
 *                  If null, the cell is empty.
 * @param onClick Callback function to be invoked when the cell is clicked.
 */
@Composable
private fun BoardCell(
    modifier: Modifier = Modifier,
    cellValue: PlayerSymbol?,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .aspectRatio(1f)
            .clickable(onClick = onClick)
            .border(2.dp, Color.Red)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {

        // Animates the drawing progress of the cell symbol (either X or O)
        val progress = animateFloatAsState(
            targetValue = if (cellValue == null) 0f else 1f,
            label = "cell-value-animation"
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            // Draw the appropriate symbol if a cell value is provided
            cellValue?.let {
                when (it) {
                    PlayerSymbol.X -> drawX(progress.value, it.color)
                    PlayerSymbol.O -> drawO(progress.value, it.color)
                }
            }

        }

    }
}

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
            width = (this.size.width / 3).coerceAtMost(15.dp.toPx()),
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
        width = (width / 3).coerceAtMost(10.dp.toPx()),
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

    val cellValue : MutableState<PlayerSymbol?> = remember { mutableStateOf(null) }
    Surface {

        Column(modifier = Modifier.fillMaxSize()) {

            Button(onClick = {

                val random = Random.nextInt(0, 11)

                cellValue.value = if (random <= 5){
                    PlayerSymbol.X
                } else {
                    PlayerSymbol.O
                }


            }) {


            }

            BoardCell(
                modifier = Modifier.width(100.dp),
                cellValue = cellValue.value
            ) { }


        }

    }

}