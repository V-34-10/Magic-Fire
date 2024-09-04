package com.magicmex.canfiree.view.games.findgame.manager

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import com.magicmex.canfiree.model.findgame.FindPair
import com.magicmex.canfiree.utils.preference.PreferenceManager
import com.magicmex.canfiree.view.games.findgame.dialog.HighScoreFindPairManager
import com.magicmex.canfiree.view.games.findgame.dialog.HighScoreFindPairManager.statsHighScoreFindPair

class FindPairClickHandler(
    private val adapterManager: FindPairAdapterManager,
    private val pairItemManager: PairItemManager,
    private val uiManager: UIManager,
    private val timerManager: TimerManager
) {
    private val delayHandler = 1000L
    private var firstPair: FindPair? = null
    private var secondPair: FindPair? = null
    private var flippingPair = false
    var stepSearchPair = 0
    var isGameStarted = false

    fun setupClickListener(context: Context, selectedLevel: String) {
        adapterManager.setOnItemClickListener { pairItem, position ->
            onPairClick(pairItem, position, context, selectedLevel)
        }
    }

    private fun onPairClick(
        pairItem: FindPair,
        position: Int,
        context: Context,
        selectedLevel: String
    ) {
        if (isInvalidClick(pairItem)) return

        startGameIfNotStarted()

        pairItem.flip = true
        pairItem.pos = position
        adapterManager.notifyItemChanged(position)

        if (firstPair == null) {
            firstPair = pairItem
        } else {
            secondPair = pairItem
            flippingPair = true
            Handler(Looper.getMainLooper()).postDelayed({
                checkMatchPair(context, selectedLevel)
                uiManager.updateTextStepPair(stepSearchPair)
            }, delayHandler)
        }
    }

    private fun isInvalidClick(pairItem: FindPair): Boolean =
        flippingPair || pairItem.flip || pairItem.match

    private fun startGameIfNotStarted() {
        if (!isGameStarted) {
            timerManager.startTimer()
            isGameStarted = true
        }
    }

    private fun checkMatchPair(context: Context, selectedLevel: String) {
        if (firstPair?.img == secondPair?.img) {
            markPairsAsMatched()
        } else {
            resetPairSelection()
        }

        stepSearchPair++
        adapterManager.notifyItemChanged(firstPair?.pos ?: -1)
        adapterManager.notifyItemChanged(secondPair?.pos ?: -1)

        resetSelection()

        if (checkGameOver(selectedLevel)) {
            saveBestStats(PreferenceManager.getPreference(context), selectedLevel)
            uiManager.showWinMessage(context)
        }
    }

    private fun markPairsAsMatched() {
        firstPair?.match = true
        secondPair?.match = true
    }

    private fun resetPairSelection() {
        firstPair?.flip = false
        secondPair?.flip = false
    }

    private fun resetSelection() {
        firstPair = null
        secondPair = null
        flippingPair = false
    }

    private fun checkGameOver(selectedLevel: String): Boolean {
        return if (selectedLevel.requiresExtraPair()) {
            pairItemManager.getPairList()
                .count { it.match } == pairItemManager.getPairList().size - 1
        } else {
            pairItemManager.getPairList().all { it.match }
        }
    }

    private fun saveBestStats(preferences: SharedPreferences, selectedLevel: String) {
        val stats = statsHighScoreFindPair[selectedLevel]!!
        if (timerManager.elapsedTime < stats.bestTime) stats.bestTime = timerManager.elapsedTime
        if (stepSearchPair < stats.bestSteps || stats.bestSteps == 0) stats.bestSteps =
            stepSearchPair

        HighScoreFindPairManager.saveStatsScoreFindPairGame(preferences)
        stepSearchPair = 0
    }

    private fun String.requiresExtraPair() = this in listOf("Level 1", "Level 3")
}