package com.magicmex.canfire.view.games.findgame.manager

fun String.getSpanCount(): Int = when (this) {
    "Level 1" -> 3
    "Level 2" -> 4
    "Level 3" -> 5
    else -> 3
}

fun String.getNumPairs(): Int = when (this) {
    "Level 1" -> 4
    "Level 2" -> 8
    "Level 3" -> 12
    else -> 4
}