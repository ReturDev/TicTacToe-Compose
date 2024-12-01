package com.returdev.tictactoe.ui.screen.authentication.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.returdev.tictactoe.R
import com.returdev.tictactoe.ui.screen.authentication.model.AuthenticationErrorType


/**
 * A composable function that displays a confirm password input field.
 * This function leverages the [GenericPasswordTextField] composable to handle the password input
 * with the ability to toggle visibility, while customizing the label and error handling for the confirm password field.
 *
 * @param modifier The modifier to be applied to the [GenericPasswordTextField]. Defaults to [Modifier].
 * @param value The current value of the confirm password input field.
 * @param passwordError An optional error type specific to the confirm password field. If non-null,
 *                     an error message related to the confirm password will be shown.
 * @param isEnabled A boolean value indicating whether the confirm password field is enabled or disabled.
 * @param onValueChange A lambda function that is invoked whenever the confirm password value changes.
 *                      It takes two parameters: the new value of the confirm password and a boolean indicating
 *                      whether the current password error is related to the confirm password field.
 */
@Composable
private fun ConfirmPasswordTextField(
    modifier : Modifier = Modifier,
    value : String,
    passwordError : AuthenticationErrorType.ConfirmPassword?,
    isEnabled : Boolean,
    onValueChange : (String, Boolean) -> Unit
) {

    GenericPasswordTextField(
        modifier = modifier,
        label = stringResource(R.string.authentication_email_confirm_password),
        value = value,
        supportingText = null,
        passwordError = passwordError,
        isEnabled = isEnabled,
        onValueChange = { newValue ->
            onValueChange(
                newValue,
                passwordError != null
            )
        }
    )

}

/**
 * A composable function that displays a password input field with the ability to toggle password visibility.
 * It supports an optional error message, a label, and a supporting text. The password visibility can be toggled
 * with a button that shows or hides the password as the user types.
 *
 * @param modifier The modifier to be applied to the [TextField] composable. Defaults to [Modifier].
 * @param label The label to display above the text field, describing the expected input (e.g., "Password").
 * @param value The current value of the password input field.
 * @param passwordError An optional error type that, if non-null, will display an error message below the text field.
 * @param supportingText Optional additional text to display beneath the text field. It will be shown if no error is present.
 * @param isEnabled A boolean value indicating whether the password field is interactive or disabled.
 * @param onValueChange A lambda function that is invoked whenever the password input value changes.
 * It takes the new value of the text field as a parameter.
 */
@Composable
private fun GenericPasswordTextField(
    modifier : Modifier = Modifier,
    label : String,
    value : String,
    passwordError : AuthenticationErrorType?,
    supportingText : String?,
    isEnabled : Boolean,
    onValueChange : (String) -> Unit
) {

    var isPasswordVisible by remember { mutableStateOf(false) }

    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        label = { Text(text = label) },
        isError = passwordError != null,
        supportingText = {
            Text(
                text = passwordError?.let { stringResource(it.stringRes) } ?: supportingText.orEmpty()
            )
        },
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                if (isPasswordVisible) {
                    Icon(
                        painter = painterResource(R.drawable.ic_password_visibility_off),
                        contentDescription = stringResource(R.string.authentication_email_hide_password)
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_password_visibility),
                        contentDescription = stringResource(R.string.authentication_email_show_password)
                    )
                }
            }
        },
        enabled = isEnabled
    )

}


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
                    R.string.authentication_email_have_not_account
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
private fun EmailAuthDialog() {

    val isSignUp = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
//        BottomChangeAuthText(isSignUpCurrentView = isSignUp.value) {
//            isSignUp.value = it
//        }
//        PasswordTextField(
//            modifier = Modifier,
//            label = "Password",
//            value = "hola",
//            supportingText = "Escribe la contraseña",
//            isEnabled = true,
//            passwordError = AuthenticationErrorType.Password(R.string.authentication_as_guest),
//            onValueChange = {}
//        )
    }

}