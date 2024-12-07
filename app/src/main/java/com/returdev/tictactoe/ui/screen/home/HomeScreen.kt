package com.returdev.tictactoe.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


/**
 * A composable function that displays a button with the provided text and a click action.
 * The button's state (enabled or disabled) is controlled by the [isEnabled] parameter.
 * This button is styled to match the minimum height of a text field and fills the available width.
 *
 * @param modifier A [Modifier] to customize the button's appearance and layout. The default is [Modifier].
 * @param text The text to be displayed inside the button.
 * @param isEnabled A boolean value that determines whether the button is enabled or disabled.
 * @param onClick A lambda function to be invoked when the button is clicked.
 */
@Composable
private fun HomeMenuButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .height(TextFieldDefaults.MinHeight)
            .fillMaxWidth(),
        enabled = isEnabled,
        onClick = onClick
    ) {
        Text(text = text)
    }
}


@Preview
@Composable
private fun HomePrev() {

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {


        }
    }

}