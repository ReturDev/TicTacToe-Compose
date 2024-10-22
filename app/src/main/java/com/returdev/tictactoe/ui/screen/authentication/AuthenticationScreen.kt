package com.returdev.tictactoe.ui.screen.authentication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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
        modifier = Modifier.fillMaxSize()
    ){
    
        AuthenticationButton(
            text = "Continue with google",
            icon = Icons.Default.Warning,
            skipIconTint = false,
            isEnabled = true
        ) {
            
        }
Spacer(modifier = Modifier.height(24.dp))
        AuthenticationButton(
            text = "Continue with Facebook",
            icon = Icons.Default.Warning,
            skipIconTint = true,
            isEnabled = true
        ) {

        }
        
    }
    

}