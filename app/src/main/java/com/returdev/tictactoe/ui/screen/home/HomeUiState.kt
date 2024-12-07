package com.returdev.tictactoe.ui.screen.home

/**
 * A sealed class representing the different states of the home screen UI.
 *
 * This class encapsulates the possible states that the home screen can be in. It helps to model the UI state
 * when loading data, successfully joining a game, or encountering an error. This sealed class is used to determine
 * which UI elements to display, such as a loading indicator, success message, or error message.
 *
 * The states include:
 * - [Initial]: Represents the initial state when no action has been taken.
 * - [Loading]: Represents the loading state when the app is processing something (e.g., joining a game).
 * - [Success]: Represents the success state when the user has successfully joined a game. It contains the game code.
 * - [Error]: Represents the error state when something goes wrong. It contains an error message.
 */
sealed class HomeUiState {

    /**
     * Represents the initial state when no action has been taken.
     */
    data object Initial : HomeUiState()

    /**
     * Represents the loading state when data is being processed.
     * Typically shown as a loading spinner.
     */
    data object Loading : HomeUiState()

    /**
     * Represents the success state when the user has successfully joined a game.
     * Contains the game code for the user to proceed to the game screen.
     *
     * @param gameCode The unique code for the game the user has joined.
     */
    data class Success(val gameCode: String) : HomeUiState()

    /**
     * Represents the error state when something goes wrong.
     * Contains an error message to inform the user about the issue.
     *
     * @param msg The error message to be displayed to the user.
     */
    data class Error(val msg: String) : HomeUiState()
}
