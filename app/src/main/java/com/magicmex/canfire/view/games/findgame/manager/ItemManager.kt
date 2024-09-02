package com.magicmex.canfire.view.games.findgame.manager

import com.magicmex.canfire.R

object ItemManager {

    fun getImagesSwitchLevel(level: String): List<Int> {
        val baseImages = getImages(level)
        val repetitions = when (level) {
            "Level 1" -> 1 to 4
            "Level 2" -> 2 to 3
            "Level 3" -> 4 to 4
            else -> 1 to 4
        }
        return baseImages.repeatAndAdd(repetitions.first, repetitions.second)
    }

    private fun List<Int>.repeatAndAdd(repeatTimes: Int, extraElements: Int): List<Int> {
        return this + List(repeatTimes) { this }.flatten() + take(extraElements)
    }

    private fun getImages(level: String): List<Int> {
        return when (level) {
            "Level 1" -> listOf(
                R.drawable.find_1,
                R.drawable.find_2,
                R.drawable.find_3,
                R.drawable.find_4
            )

            "Level 2" -> listOf(
                R.drawable.find_1,
                R.drawable.find_2,
                R.drawable.find_3,
                R.drawable.find_4,
                R.drawable.find_5,
                R.drawable.find__6,
                R.drawable.find_7,
                R.drawable.find_8
            )

            "Level 3" -> listOf(
                R.drawable.find_1,
                R.drawable.find_2,
                R.drawable.find_3,
                R.drawable.find_4,
                R.drawable.find_5,
                R.drawable.find__6,
                R.drawable.find_7,
                R.drawable.find_8,
                R.drawable.find_9,
                R.drawable.find_10,
                R.drawable.find_11,
                R.drawable.find_12
            )

            else -> listOf(
                R.drawable.find_1,
                R.drawable.find_2,
                R.drawable.find_3,
                R.drawable.find_4
            )
        }
    }
}