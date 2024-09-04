package com.magicmex.canfiree.model.findgame

data class FindPair(
    val img: Int,
    var flip: Boolean = false,
    var match: Boolean = false,
    var pos: Int = -1
)