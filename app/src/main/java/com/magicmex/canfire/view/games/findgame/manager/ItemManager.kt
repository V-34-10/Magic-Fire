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
                R.drawable.find_1,
                R.drawable.find_2,
                R.drawable.find_3,
                R.drawable.find_4
            )

            "Level 3" -> listOf(
                R.drawable.find_1,
                R.drawable.find_2,
                R.drawable.find_3,
                R.drawable.find_4,
                R.drawable.find_1,
                R.drawable.find_2,
                R.drawable.find_3,
                R.drawable.find_4,
                R.drawable.find_1,
                R.drawable.find_2,
                R.drawable.find_3,
                R.drawable.find_4
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