package com.returdev.tictactoe.ui.screen.authentication.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.returdev.tictactoe.R

/**
 * A composable function that displays a row of text elements at the bottom of the authentication screen.
 * It shows a message indicating whether the user is currently on the sign-up or sign-in view,
 * and provides a button to toggle between the two views.
 *
 * @param modifier The modifier to be applied to the outer [Row] composable. Defaults to [Modifier].
 * @param isSignUpCurrentView A boolean value indicating whether the current view is the sign-up view.
 * @param onChangeAuth A lambda function that is invoked when the user clicks the toggle button.
 * It takes a boolean parameter that is the opposite of the current view state and is used
 * to switch between sign-up and sign-in modes.
 */
@Composable
private fun BottomChangeAuthText(
    modifier : Modifier = Modifier,
    isSignUpCurrentView : Boolean,
    onChangeAuth : (Boolean) -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(
                id = if (isSignUpCurrentView)
                    R.string.authentication_email_have_account
                else
                    R.string.athentication_email_have_not_account
            )
        )

        TextButton(
            onClick = { onChangeAuth(!isSignUpCurrentView) }
        ) {

            Text(
                text = stringResource(
                    id = if (isSignUpCurrentView)
                        R.string.authentication_email_sign_in
                    else
                        R.string.authentication_email_sign_up
                )
            )

        }

    }

}

@Preview
@Composable
private fun EmailAuthDialog(){

    val isSignUp = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        BottomChangeAuthText(isSignUpCurrentView = isSignUp.value) {
            isSignUp.value = it
        }
    }

}