package com.magicmex.canfiree.model.findgame

data class HighScoreFindPair(
    var bestTime: Long = Long.MAX_VALUE,
    var bestSteps: Int = 0
)