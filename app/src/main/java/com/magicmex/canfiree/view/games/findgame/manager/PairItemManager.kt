package com.magicmex.canfiree.view.games.findgame.manager

import com.magicmex.canfiree.model.findgame.FindPair

class PairItemManager {
    private val pairList = mutableListOf<FindPair>()
    private val imageList = mutableListOf<Int>()

    fun setupPairItems(selectedLevel: String) {
        pairList.clear()
        imageList.apply {
            clear()
            addAll(ItemManager.getImagesSwitchLevel(selectedLevel))
        }

        var position = 0
        repeat(selectedLevel.getNumPairs()) {
            val imageRes = imageList[it]
            pairList.add(FindPair(imageRes, pos = position++))
            pairList.add(FindPair(imageRes, pos = position++))
        }

        if (selectedLevel.requiresExtraPair()) pairList.add(
            FindPair(imageList[0], pos = position++)
        )

        pairList.shuffle()
    }

    fun getPairList(): List<FindPair> = pairList

    fun resetPairs() {
        pairList.forEach { it.reset() }
    }

    private fun FindPair.reset() {
        flip = false
        match = false
    }

    private fun String.requiresExtraPair() = this in listOf("Level 1", "Level 3")
}

fun String.getNumPairs(): Int = when (this) {
    "Level 1" -> 4
    "Level 2" -> 8
    "Level 3" -> 12
    else -> 4
}