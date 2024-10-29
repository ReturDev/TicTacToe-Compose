package com.returdev.tictactoe.ui.screen.authentication.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.returdev.tictactoe.R

/**
 * Enum class representing the available authentication providers.
 *
 * @property stringRes Resource ID for the string that represents the provider's name.
 * @property iconRes Resource ID for the drawable icon associated with the provider.
 * @property skipIconTint Boolean flag to determine whether the icon should skip tinting.
 */
enum class AuthProvider(
    @StringRes val stringRes: Int,
    @DrawableRes val iconRes: Int,
    val skipIconTint: Boolean
) {

    /** Authentication using email. */
    EMAIL(R.string.authentication_with_email, R.drawable.ic_email_auth, false),

    /** Authentication using Google account. */
    GOOGLE(R.string.authentication_with_google, R.drawable.ic_google_auth, true),

    /** Authentication as a guest user. */
    GUEST(R.string.authentication_as_guest, R.drawable.ic_guest_auth, false);
}

