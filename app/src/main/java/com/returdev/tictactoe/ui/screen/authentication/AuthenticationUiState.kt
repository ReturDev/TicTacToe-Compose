package com.returdev.tictactoe.ui.screen.authentication

import com.returdev.tictactoe.ui.screen.authentication.model.AuthenticationErrorType

/**
 * Represents the various states of the authentication user interface.
 * This sealed class is used to model the progression and outcomes of the authentication process,
 * including loading, success, and error states.
 */
sealed class AuthenticationUiState {

    /**
     * Represents the initial state of the authentication UI, typically before any user interaction occurs.
     */
    data object Initial : AuthenticationUiState()

    /**
     * Represents a loading state for email-based authentication actions.
     * This state is typically used to indicate that an email sign-in or sign-up operation is in progress.
     */
    data object EmailAuthLoading : AuthenticationUiState()

    /**
     * Represents a general loading state for authentication actions.
     * This can be used for operations not specifically tied to email authentication.
     */
    data object GeneralAuthLoading : AuthenticationUiState()

    /**
     * Represents a successful authentication state.
     * This state indicates that the user has been successfully authenticated.
     */
    data object AuthSuccess : AuthenticationUiState()

    /**
     * Represents an error state during the authentication process.
     * This state holds an instance of [AuthenticationErrorType] to describe the specific error encountered.
     *
     * @property error An [AuthenticationErrorType] instance providing details about the authentication error.
     */
    data class AuthError(
        val error: AuthenticationErrorType
    ) : AuthenticationUiState()
}
