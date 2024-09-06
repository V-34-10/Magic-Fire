package com.magicmex.canfiree.view.games.findgame.manager

import com.magicmex.canfiree.R

data class LevelConfig(
    val images: List<Int>,
    val repeatTimes: Int,
    val extraElements: Int
)

object ItemManager {

    private val levelConfigurations = mapOf(
        "Level 1" to LevelConfig(
            images = listOf(
                R.drawable.find_1,
                R.drawable.find_2,
                R.drawable.find_3,
                R.drawable.find_4
            ),
            repeatTimes = 1,
            extraElements = 4
        ),
        "Level 2" to LevelConfig(
            images = listOf(
                R.drawable.find_1, R.drawable.find_2, R.drawable.find_3, R.drawable.find_4,
                R.drawable.find_5, R.drawable.find__6, R.drawable.find_7, R.drawable.find_8
            ),
            repeatTimes = 2,
            extraElements = 3
        ),
        "Level 3" to LevelConfig(
            images = listOf(
                R.drawable.find_1, R.drawable.find_2, R.drawable.find_3, R.drawable.find_4,
                R.drawable.find_5, R.drawable.find__6, R.drawable.find_7, R.drawable.find_8,
                R.drawable.find_9, R.drawable.find_10, R.drawable.find_11, R.drawable.find_12
            ),
            repeatTimes = 4,
            extraElements = 4
        )
    )

    fun getImagesSwitchLevel(level: String): List<Int> {
        val config = levelConfigurations[level] ?: levelConfigurations["Level 1"]!!
        return config.images.repeatAndAdd(config.repeatTimes, config.extraElements)
    }

    private fun List<Int>.repeatAndAdd(repeatTimes: Int, extraElements: Int): List<Int> {
        return this + List(repeatTimes) { this }.flatten() + take(extraElements)
    }
}