package com.returdev.tictactoe.ui.screen.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Visibility
import com.returdev.tictactoe.R
import com.returdev.tictactoe.ui.screen.authentication.component.EmailAuthDialog
import com.returdev.tictactoe.ui.screen.authentication.model.AuthProvider
import com.returdev.tictactoe.ui.screen.authentication.model.AuthenticationUiState


/**
 * Composable function that displays the authentication screen.
 *
 * @param modifier Modifier for customizing the layout of the composable.
 * @param state Holds the current UI state for authentication, represented by `AuthenticationUiState`.
 * Used to manage loading states and error handling.
 * @param onEmailSignUp Callback invoked when the user initiates an email sign-up. Requires email, password,
 * and confirm password parameters.
 * @param onEmailSignIn Callback invoked when the user initiates an email sign-in, receiving email and password as parameters.
 * @param onGoogleSignIn Callback invoked for Google sign-in.
 * @param onGuestSignIn Callback invoked for guest sign-in.
 * @param resetEmailAuthErrorState Callback to reset the error state of email authentication.
 */
@Composable
private fun AuthenticationScreenComposable(
    modifier : Modifier = Modifier,
    state : State<AuthenticationUiState>,
    onEmailSignUp : (email : String, password : String, confirmPassword : String) -> Unit,
    onEmailSignIn : (email : String, password : String) -> Unit,
    onGoogleSignIn : () -> Unit,
    onGuestSignIn : () -> Unit,
    resetEmailAuthErrorState : () -> Unit
) {

    var showEmailAuthDialog by remember {
        mutableStateOf(false)
    }

    if (showEmailAuthDialog){

        EmailAuthDialog(
            state = state,
            resetErrorState = resetEmailAuthErrorState,
            onSignIn = onEmailSignIn,
            onSignUp = onEmailSignUp
        ) {
            showEmailAuthDialog = false
        }

    }


    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {

        val (logoCons, buttonsCons, loadingCons) = createRefs()
        val verticalChain =
            createVerticalChain(logoCons, buttonsCons, chainStyle = ChainStyle.Spread)

        constrain(verticalChain) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom, 48.dp)
        }


        Image(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(1f)
                .constrainAs(logoCons) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            painter = painterResource(R.drawable.app_logo),
            contentDescription = null
        )

        AuthenticationButtonsColumn(
            modifier = Modifier.constrainAs(buttonsCons) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            authProviders = AuthProvider.entries,
            onSignIn = { authProvider ->
                when (authProvider) {
                    AuthProvider.EMAIL -> showEmailAuthDialog = true
                    AuthProvider.GOOGLE -> onGoogleSignIn()
                    AuthProvider.GUEST -> onGuestSignIn()
                }
            },
            isEnabled = state.value != AuthenticationUiState.GeneralAuthLoading
        )

        CircularProgressIndicator(
            modifier = Modifier.constrainAs(loadingCons) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                visibility = if (state.value is AuthenticationUiState.GeneralAuthLoading)
                    Visibility.Visible
                else
                    Visibility.Gone
            }
        )

    }

}

/**
 * Composable function that displays a column of sign-in buttons.
 *
 * @param modifier Optional modifier for customizing the layout of this composable.
 * @param authProviders List of available authentication providers to display as sign-in options.
 * Each provider includes a text and icon resource.
 * @param onSignIn Lambda function triggered when the user selects a sign-in provider.
 * It receives the selected `AuthProvider` as a parameter.
 * @param isEnabled Boolean flag indicating whether the sign-in buttons are enabled or disabled.
 */
@Composable
private fun AuthenticationButtonsColumn(
    modifier : Modifier = Modifier,
    authProviders : List<AuthProvider>,
    onSignIn : (AuthProvider) -> Unit,
    isEnabled : Boolean
) {

    Card(
        modifier = modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp)
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.authentication_sign_in_to_play),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                authProviders.forEach { authProvider ->

                    AuthenticationButton(
                        text = stringResource(id = authProvider.stringRes),
                        icon = ImageVector.vectorResource(authProvider.iconRes),
                        skipIconTint = authProvider.skipIconTint,
                        isEnabled = isEnabled
                    ) {
                        onSignIn(authProvider)
                    }

                }

            }

        }

    }


}

/**
 * A private composable function to display a button for authentication.
 *
 * @param modifier Modifier to be applied to the Button composable.
 * @param text The text to be displayed on the button.
 * @param icon The icon to be displayed on the button.
 * @param skipIconTint Boolean indicating if the icon tint should be skipped.
 * @param isEnabled Boolean to enable or disable the button.
 * @param onClick A lambda to handle button clicks.
 */
@Composable
private fun AuthenticationButton(
    modifier : Modifier = Modifier,
    text : String,
    icon : ImageVector,
    skipIconTint : Boolean,
    isEnabled : Boolean,
    onClick : () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        enabled = isEnabled
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = icon,
            contentDescription = null,
            tint = if (skipIconTint) Color.Unspecified else LocalContentColor.current
        )

        Spacer(modifier = Modifier.width(24.dp))

        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}