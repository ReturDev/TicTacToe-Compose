package com.returdev.tictactoe.ui.screen.authentication.model

import androidx.annotation.StringRes

/**
 * Represents the various types of authentication errors.
 * Each error type is associated with a string resource ID for displaying an appropriate error message.
 *
 * @property stringRes The string resource ID corresponding to the error message for this error type.
 */
sealed class AuthenticationErrorType(
    @StringRes val stringRes: Int
) {

    /**
     * Represents an authentication error related to email input.
     *
     * @param stringRes The string resource ID for the email error message.
     */
    class Email(@StringRes stringRes: Int) : AuthenticationErrorType(stringRes)

    /**
     * Represents an authentication error related to password input.
     *
     * @param stringRes The string resource ID for the password error message.
     */
    class Password(@StringRes stringRes: Int) : AuthenticationErrorType(stringRes)

    /**
     * Represents an authentication error related to confirm password input.
     * This occurs when the confirmation password does not match the original password.
     *
     * @param stringRes The string resource ID for the confirm password error message.
     */
    class ConfirmPassword(@StringRes stringRes: Int) : AuthenticationErrorType(stringRes)

    /**
     * Represents a generic authentication error not specific to email, password, or confirmation.
     *
     * @param stringRes The string resource ID for the generic error message.
     */
    class Generic(@StringRes stringRes: Int) : AuthenticationErrorType(stringRes)

}
