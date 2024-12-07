package com.returdev.tictactoe.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.returdev.tictactoe.R

/**
 * A composable that displays the application's logo image.
 *
 * This composable renders the logo image of the app, scaled to 70% of the available width and cropped to fit.
 * It accepts an optional [modifier] parameter to customize the appearance and layout of the logo.
 *
 * @param modifier A [Modifier] that can be used to adjust the layout, size, and other properties of the logo image.
 **/
@Composable
private fun HomeLogo(
    modifier : Modifier = Modifier
) {

    Image(
        modifier = modifier
            .fillMaxWidth(0.7f)
            .scale(1f),
        painter = painterResource(id = R.drawable.app_logo),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

}

/**
 * A composable that displays a menu for joining a game or creating a new one.
 * It contains a text field for entering a game code, buttons to join or create a game,
 * and an option to paste a previously copied game code.
 *
 * @param modifier A [Modifier] to customize the layout and appearance of the menu. The default is [Modifier].
 * @param isEnabled A boolean value that determines whether the text field and buttons are enabled or disabled.
 * @param pasteGameCode A lambda function that returns a string representing the game code, or null if no code is available to paste.
 * @param joinToGame A lambda function invoked when the user attempts to join a game using the entered game code.
 * @param createNewGame A lambda function invoked when the user attempts to create a new game.
 */
@Composable
private fun HomeButtonsMenu(
    modifier : Modifier = Modifier,
    isEnabled : Boolean,
    pasteGameCode : () -> String?,
    joinToGame : (String) -> Unit,
    createNewGame : () -> Unit
) {

    var gameCode by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(0.95f),
            value = gameCode,
            onValueChange = {
                if (gameCode.length <= 50){
                    gameCode = it
                }
            },
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(R.string.home_screen_game_code_placeholder)
                )
            },
            trailingIcon = {
                if (gameCode.isNotEmpty()){
                    IconButton(
                        onClick = { gameCode = "" }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(R.string.home_screen_clear_game_code)
                        )
                    }
                }
            },
            leadingIcon = {
                IconButton(
                    onClick = {
                        pasteGameCode()?.let {
                            gameCode = it
                        }
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_paste),
                        contentDescription = stringResource(R.string.home_screen_paste_code)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = {joinToGame(gameCode)}
            ),
            enabled = isEnabled
        )

        HomeMenuButton(
            text = stringResource(R.string.home_screen_join_to_game),
            isEnabled = isEnabled && gameCode.isNotEmpty()
        ) { joinToGame(gameCode) }

        Text(
            text = stringResource(R.string.home_screen_or_divider),
            fontWeight = FontWeight.Bold
        )

        HomeMenuButton(
            text = stringResource(R.string.home_screen_new_game),
            isEnabled = isEnabled,
            onClick = createNewGame
        )

    }

}

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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HomeLogo()

            HomeButtonsMenu(isEnabled = true, pasteGameCode = {""}, joinToGame = {}, createNewGame = {})

        }
    }

}