package com.returdev.tictactoe.ui.screen.authentication.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Visibility
import com.returdev.tictactoe.R
import com.returdev.tictactoe.ui.screen.authentication.AuthenticationUiState
import com.returdev.tictactoe.ui.screen.authentication.model.AuthenticationErrorType

/**
 * A composable function that displays an email authentication dialog within a modal bottom sheet.
 * This dialog provides a user interface for both sign-in and sign-up workflows, with the ability to handle
 * authentication errors and close the dialog when needed.
 *
 * @param state A [State] object representing the current state of the authentication UI.
 *              This is used to manage the UI's behavior and display appropriate error messages.
 * @param resetErrorState A lambda function invoked to reset the error state when the user modifies input fields.
 * @param onSignIn A lambda function invoked when the user attempts to sign in.
 *                 It takes two parameters: the email and password values entered by the user.
 * @param onSignUp A lambda function invoked when the user attempts to sign up.
 *                 It takes three parameters: the email, password, and confirm password values entered by the user.
 * @param onClose A lambda function invoked when the dialog is dismissed, either by the user or programmatically.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailAuthDialog(
    state: State<AuthenticationUiState>,
    resetErrorState: () -> Unit,
    onSignIn: (email: String, password: String) -> Unit,
    onSignUp: (email: String, password: String, confirmPassword: String) -> Unit,
    onClose: () -> Unit
) {

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    // Modal bottom sheet for displaying the authentication UI
    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = bottomSheetState,
        dragHandle = null
    ) {
        // Composable handling the authentication logic and UI
        EmailAuthComposable(
            state = state.value,
            resetErrorState = resetErrorState,
            onSignIn = onSignIn,
            onSignUp = onSignUp
        )
    }
}

/**
 * A composable function that renders the email authentication screen, including both sign-in and sign-up views.
 * It dynamically switches between the sign-in and sign-up screens based on user interaction and manages the authentication state.
 *
 * @param modifier The modifier to be applied to the root container of the authentication screen. Defaults to [Modifier].
 * @param state The current state of the authentication UI. Used to manage the enabled state of fields and display validation errors.
 * @param resetErrorState A lambda function invoked to reset the error state when a field value changes.
 * @param onSignIn A lambda function invoked when the user attempts to sign in.
 *                 It takes two parameters: the email and password values entered by the user.
 * @param onSignUp A lambda function invoked when the user attempts to sign up.
 *                 It takes three parameters: the email, password, and confirm password values entered by the user.
 */
@Composable
private fun EmailAuthComposable(
    modifier: Modifier = Modifier,
    state: AuthenticationUiState,
    resetErrorState: () -> Unit,
    onSignIn: (email: String, password: String) -> Unit,
    onSignUp: (email: String, password: String, confirmPassword: String) -> Unit
) {
    var isSignUpScreen by remember {
        mutableStateOf(false)
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        val (titleCons, signUpCons, signInCons, changeViewCons) = createRefs()

        EmailAuthDialogTitle(
            modifier = Modifier.constrainAs(titleCons) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            },
            title = stringResource(
                id = if (isSignUpScreen) {
                    R.string.authentication_email_sign_up
                } else {
                    R.string.authentication_email_sign_in
                }
            )
        )

        SignUpView(
            modifier = Modifier
                .constrainAs(signUpCons) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(titleCons.bottom)
                    bottom.linkTo(changeViewCons.top)
                    visibility = if (isSignUpScreen) Visibility.Visible else Visibility.Gone
                }
                .padding(vertical = 8.dp),
            state = state,
            resetErrorState = resetErrorState,
            onSignUp = onSignUp
        )

        SignInView(
            modifier = Modifier
                .constrainAs(signInCons) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(titleCons.bottom)
                    bottom.linkTo(changeViewCons.top)
                    visibility = if (isSignUpScreen) Visibility.Gone else Visibility.Visible
                }
                .padding(vertical = 8.dp),
            state = state,
            resetErrorState = resetErrorState,
            onSignIn = onSignIn
        )

        BottomChangeAuthText(
            modifier = Modifier.constrainAs(changeViewCons) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            isSignUpCurrentView = isSignUpScreen
        ) {
            isSignUpScreen = !isSignUpScreen
        }
    }
}


/**
 * A composable function that displays a bold and stylized title for the authentication dialog.
 *
 * @param modifier The modifier to be applied to the [Text] composable. Defaults to [Modifier].
 * @param title The title text to be displayed in the dialog. Typically reflects the current authentication action
 *              (e.g., "Sign In" or "Sign Up").
 */
@Composable
private fun EmailAuthDialogTitle(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        modifier = modifier,
        text = title,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.displaySmall
    )
}

/**
 * A composable function that represents the sign-in view in the authentication flow.
 * This view provides input fields for email and password, and a button to trigger the sign-in process.
 * It handles validation errors and manages the state of the input fields.
 *
 * @param modifier The modifier to be applied to the root container of the sign-in view. Defaults to [Modifier].
 * @param state The current state of the authentication UI. Used to determine the enabled state of the fields
 *              and to display validation errors for the email and password inputs.
 * @param resetErrorState A lambda function invoked to reset the error state when a field value changes.
 * @param onSignIn A lambda function invoked when the sign-in button is clicked.
 *                 It takes two parameters: the email and password values entered by the user.
 */
@Composable
private fun SignInView(
    modifier: Modifier = Modifier,
    state: AuthenticationUiState,
    resetErrorState: () -> Unit,
    onSignIn: (email: String, password: String) -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isEnabled by remember(state) { mutableStateOf(state !is AuthenticationUiState.EmailAuthLoading) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

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

            PasswordTextField(
                value = password,
                showSupportingText = false,
                passwordError = (state as? AuthenticationUiState.AuthError)?.error as? AuthenticationErrorType.Password,
                isEnabled = isEnabled,
            ) { newValue, isError ->
                if (isError) {
                    resetErrorState()
                }
                password = newValue
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onSignIn(email, password) },
            enabled = isEnabled && email.isNotBlank() && password.isNotBlank()
        ) {
            Text(text = stringResource(R.string.authentication_email_sign_in))
        }

    }

}


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