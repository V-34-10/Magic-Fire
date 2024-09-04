package com.magicmex.canfiree.view.games.findgame.manager

import android.content.Context
import androidx.viewbinding.ViewBinding

class FindPairGameManager(
    private val context: Context,
    binding: ViewBinding
) {
    private val timerManager = TimerManager(binding)
    private val uiManager = UIManager(binding)
    private val adapterManager = FindPairAdapterManager(binding, context, uiManager)
    private val pairItemManager = PairItemManager()
    private val clickHandler = FindPairClickHandler(
        adapterManager, pairItemManager, uiManager, timerManager
    )

    fun initGame(selectedLevel: String) {
        adapterManager.initRecyclerView(context, selectedLevel)
        pairItemManager.setupPairItems(selectedLevel)
        adapterManager.setupAdapter(pairItemManager.getPairList())
        clickHandler.setupClickListener(context, selectedLevel)
    }

    fun resetGame() {
        pairItemManager.resetPairs()
        adapterManager.resetAdapter()
        uiManager.resetUI()
        clickHandler.stepSearchPair = 0
        timerManager.stopTimer()
        clickHandler.isGameStarted = false
    }
}