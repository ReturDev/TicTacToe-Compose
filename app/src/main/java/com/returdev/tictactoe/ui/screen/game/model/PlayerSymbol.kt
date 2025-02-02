package com.returdev.tictactoe.ui.screen.game.model

import androidx.compose.ui.graphics.Color

/**
 * Enum class representing player symbols in a game, each associated with a specific color.
 *
 * @property color The color associated with the player symbol.
 */
enum class PlayerSymbol(
    val color : Color
) {

    X(Color.Red),
    O(Color.Blue)

}