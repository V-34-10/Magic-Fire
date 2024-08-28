package com.magicmex.canfire.view.games.findgame.manager

import com.magicmex.canfire.R

object ItemManager {

    fun getImagesSwitchLevel(level: String): List<Int> {
        val baseImages = getImages(level)
        return when (level) {
            "Level 1" -> baseImages + baseImages.take(4)
            "Level 2" -> baseImages + baseImages + baseImages.take(3)
            "Level 3" -> baseImages + baseImages + baseImages + baseImages + baseImages.take(4)
            else -> baseImages + baseImages.take(4)
        }
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