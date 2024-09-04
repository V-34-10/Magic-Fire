package com.magicmex.canfiree.view.games.kenogame.manager

fun String.getMaxNumbers(): Int = when (this) {
    "Level 1" -> 5
    "Level 2" -> 4
    "Level 3" -> 3
    else -> 5
}