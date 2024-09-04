package com.magicmex.canfiree.model.kenogame

data class KenoGame(
    val index: Int,
    var isSelected: Boolean = false,
    var isWinning: Boolean = false
)