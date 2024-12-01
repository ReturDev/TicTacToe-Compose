package com.returdev.tictactoe.ui.screen.authentication.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.unit.dp
import com.returdev.tictactoe.R
import com.returdev.tictactoe.ui.screen.authentication.AuthenticationUiState
import com.returdev.tictactoe.ui.screen.authentication.model.AuthenticationErrorType


/**
 * A composable function that represents the sign-up view in the authentication flow.
 * This view includes fields for entering email, password, and confirm password, and a button for submitting
 * the sign-up request. It handles validation errors and manages the state of the input fields.
 *
 * @param modifier The modifier to be applied to the root container of the sign-up view. Defaults to [Modifier].
 * @param state The current state of the authentication UI. Used to determine the enabled state of the fields
 *              and to display validation errors.
 * @param resetErrorState A lambda function invoked to reset the error state when a field value changes.
 * @param onSignUp A lambda function invoked when the sign-up button is clicked.
 *                 It takes three parameters: the email, password, and confirm password values entered by the user.
 */
@Composable
private fun SignUpView(
    modifier : Modifier = Modifier,
    state : AuthenticationUiState,
    resetErrorState : () -> Unit,
    onSignUp : (email : String, password : String, confirmPassword : String) -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val isEnabled by remember(state) { mutableStateOf(state !is AuthenticationUiState.EmailAuthLoading) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // Email Input Field
            EmailTextField(
                value = email,
                emailError = (state as? AuthenticationUiState.AuthError)?.error as? AuthenticationErrorType.Email,
                isEnabled = isEnabled,
            ) { newValue, isError ->
                if (isError) {
                    resetErrorState()
                }
                email = newValue
            }

            // Password Input Field
            PasswordTextField(
                value = password,
                showSupportingText = true,
                passwordError = (state as? AuthenticationUiState.AuthError)?.error as? AuthenticationErrorType.Password,
                isEnabled = isEnabled,
            ) { newValue, isError ->
                if (isError) {
                    resetErrorState()
                }
                password = newValue
            }

            // Confirm Password Input Field
            ConfirmPasswordTextField(
                value = confirmPassword,
                passwordError = (state as? AuthenticationUiState.AuthError)?.error as? AuthenticationErrorType.ConfirmPassword,
                isEnabled = isEnabled
            ) { newValue, isError ->
                if (isError) {
                    resetErrorState()
                }
                confirmPassword = newValue
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sign-Up Button
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onSignUp(email, password, confirmPassword) },
            enabled = isEnabled && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
        ) {
            Text(text = stringResource(R.string.authentication_email_sign_up))
        }

    }

}


/**
 * A composable function that displays an email input field with error handling and optional supporting text.
 * This function provides a label for the email input, displays an error message if the email is invalid,
 * and allows the user to input their email address with real-time validation.
 *
 * @param modifier The modifier to be applied to the [TextField] composable. Defaults to [Modifier].
 * @param value The current value of the email input field.
 * @param emailError An optional error type related to the email field. If non-null, an error message
 *                   specific to the email validation will be displayed beneath the field.
 * @param isEnabled A boolean value indicating whether the email field is enabled or disabled.
 * @param onValueChange A lambda function that is invoked whenever the email value changes.
 *                      It takes two parameters: the new value of the email and a boolean indicating
 *                      whether the current error is not null.
 */
@Composable
private fun EmailTextField(
    modifier : Modifier = Modifier,
    value : String,
    emailError : AuthenticationErrorType.Email?,
    isEnabled : Boolean,
    onValueChange : (String, Boolean) -> Unit
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue -> onValueChange(newValue, emailError != null) },
        label = {
            Text(text = stringResource(id = R.string.authentication_email_email_label))
        },
        isError = emailError != null,
        supportingText = {
            emailError?.let {
                Text(text = stringResource(id = it.stringRes))
            }
        },
        singleLine = true,
        enabled = isEnabled
    )
}

/**
 * A composable function that displays a password input field with optional supporting text and error handling.
 * This function uses [GenericPasswordTextField] to manage password input, including visibility toggle,
 * and provides a label, optional supporting text, and error handling for the password field.
 *
 * @param value The current value of the password input field.
 * @param showSupportingText A boolean value that determines whether supporting text should be shown beneath
 *                           the password field. If `true`, a predefined message about the password length
 *                           requirement is displayed.
 * @param passwordError An optional error type related to the password field. If non-null, an error message
 *                      will be displayed below the field.
 * @param isEnabled A boolean value indicating whether the password field is enabled or disabled.
 * @param onValueChange A lambda function that is invoked whenever the password value changes.
 *                      It takes two parameters: the new value of the password and a boolean indicating
 *                      whether the current error is not null.
 */
@Composable
private fun PasswordTextField(
    value : String,
    showSupportingText : Boolean,
    passwordError : AuthenticationErrorType.Password?,
    isEnabled : Boolean,
    onValueChange : (String, Boolean) -> Unit
) {
    GenericPasswordTextField(
        label = stringResource(id = R.string.authentication_email_password),
        value = value,
        passwordError = passwordError,
        supportingText = if (showSupportingText) {
            stringResource(id = R.string.authentication_email_password_length_required)
        } else null,
        isEnabled = isEnabled,
        onValueChange = { newValue -> onValueChange(newValue, passwordError != null) }
    )
}

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
 *                      whether the current password error is not null.
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
                text = passwordError?.let { stringResource(it.stringRes) }
                    ?: supportingText.orEmpty()
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun EmailAuthDialog() {

    val isSignUp = remember { mutableStateOf(true) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (isSignUp.value) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = {}
        ) {
            Surface {
                SignUpView(
                    modifier = Modifier.padding(16.dp),
                    state = AuthenticationUiState.AuthSuccess,
                    resetErrorState = {}
                ) { _, _, _ -> }

            }
        }
    }


    Surface(modifier = Modifier.fillMaxSize()) {

        Column {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { isSignUp.value = true }
            ) { }
        }

    }
}