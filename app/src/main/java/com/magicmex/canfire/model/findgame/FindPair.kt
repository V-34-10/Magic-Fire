package com.magicmex.canfire.model.findgame

data class FindPair(
    val img: Int,
    var flip: Boolean = false,
    var match: Boolean = false,
    var pos: Int = -1
)