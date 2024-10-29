package com.returdev.tictactoe.ui.screen.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.returdev.tictactoe.R
import com.returdev.tictactoe.ui.screen.authentication.model.AuthProvider


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
private fun SignInButtonsColumn(
    modifier : Modifier = Modifier,
    authProviders : List<AuthProvider>,
    onSignIn : (AuthProvider) -> Unit,
    isEnabled : Boolean
){

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

                authProviders.forEach{ authProvider ->

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

@Preview
@Composable
private fun AuthenticationScreenPrev(){

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ){

        SignInButtonsColumn(
            authProviders = AuthProvider.entries,
            onSignIn = { aut -> },
            isEnabled = true
        )

//        AuthenticationButton(
//            text = "Continue with google",
//            icon = Icons.Default.Warning,
//            skipIconTint = false,
//            isEnabled = true
//        ) {
//
//        }
//Spacer(modifier = Modifier.height(24.dp))
//        AuthenticationButton(
//            text = "Continue with Facebook",
//            icon = Icons.Default.Warning,
//            skipIconTint = true,
//            isEnabled = true
//        ) {
//
//        }
        
    }
    

}