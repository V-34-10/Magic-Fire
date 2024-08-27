package com.magicmex.canfire.view.games.findgame.manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.magicmex.canfire.R
import com.magicmex.canfire.adapter.FindPairAdapter
import com.magicmex.canfire.databinding.FragmentFindPairGameBinding
import com.magicmex.canfire.model.FindPair
import com.magicmex.canfire.view.games.findgame.dialog.HighScoreFindPairManager
import com.magicmex.canfire.view.games.findgame.dialog.HighScoreFindPairManager.statsHighScoreFindPair

object ManagerFindPair {
    private var delay = 1000L
    private lateinit var preferences: SharedPreferences
    private lateinit var recyclerViewSceneGame: RecyclerView

    private lateinit var adapterPair: FindPairAdapter
    private val pairList = mutableListOf<FindPair>()

    private var firstPair: FindPair? = null
    private var secondPair: FindPair? = null
    private var flippingPair = false
    private val imageListPair = mutableListOf<Int>()

    private var selectedLevelPairGame: String = ""
    private var stepSearchPair = 0

    private var timer: CountDownTimer? = null
    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var gameStarted = false

    fun initFindPairGame(binding: ViewBinding, context: Context) {
        preferences = GameSettings.getPreference(context)

        selectedLevelPairGame = GameSettings.selectedLevel

        adapterPair = FindPairAdapter(pairList, selectedLevelPairGame)

        recyclerViewSceneGame = when (binding) {
            is FragmentFindPairGameBinding -> binding.sceneGame
            else -> throw IllegalArgumentException("Unsupported binding type")
        }

        recyclerViewSceneGame.layoutManager =
            GridLayoutManager(context, selectedLevelPairGame.getSpanCount())
        recyclerViewSceneGame.adapter = adapterPair

        insertPairItems()

        adapterPair.onFindPairClick = { cardItem, position ->
            handlePairClick(cardItem, position, binding, context)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun insertPairItems() {
        var position = 0
        imageListPair.clear()
        imageListPair.addAll(ItemManager.getImagesSwitchLevel(selectedLevelPairGame))

        pairList.clear()
        for (i in 0 until selectedLevelPairGame.getNumPairs()) {
            val imageRes = imageListPair[i]
            pairList.add(FindPair(imageRes, pos = position++))
            pairList.add(FindPair(imageRes, pos = position++))
        }

        if (selectedLevelPairGame in listOf("Level 1", "Level 3")) {
            pairList.add(FindPair(imageListPair[0], pos = position++))
        }

        pairList.shuffle()
        adapterPair.notifyDataSetChanged()
    }

    private fun handlePairClick(
        pairItem: FindPair,
        position: Int,
        binding: ViewBinding,
        context: Context
    ) {
        if (flippingPair || pairItem.flip || pairItem.match) return

        if (!gameStarted) {
            startTimer()
            gameStarted = true
        }

        pairItem.flip = true
        pairItem.pos = position
        adapterPair.notifyItemChanged(position)

        when (firstPair) {
            null -> firstPair = pairItem
            else -> {
                secondPair = pairItem
                flippingPair = true
                Handler(Looper.getMainLooper()).postDelayed({
                    checkMatchPair(context)
                    updateTextStepPair(binding)
                }, delay)
            }
        }
    }

    private fun startTimer() {
        startTime = System.currentTimeMillis()
        timer = object : CountDownTimer(Long.MAX_VALUE, delay) {
            override fun onTick(millisUntilFinished: Long) {
                elapsedTime = System.currentTimeMillis() - startTime
            }

            override fun onFinish() {}
        }.start()
    }

    private fun stopTimer() {
        timer?.cancel()
        startTime = 0
        elapsedTime = 0
    }

    private fun checkMatchPair(context: Context) {
        val firstPos = firstPair?.pos ?: -1
        val secondPos = secondPair?.pos ?: -1

        if (firstPair?.img == secondPair?.img) {
            firstPair?.match = true
            secondPair?.match = true
        } else {
            firstPair?.flip = false
            secondPair?.flip = false
        }
        stepSearchPair++
        adapterPair.notifyItemChanged(firstPos)
        adapterPair.notifyItemChanged(secondPos)

        firstPair = null
        secondPair = null
        flippingPair = false

        if (checkGameOver()) {
            saveBestStatsFindGame()
            showWinMessage(context)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetFindPairGame(binding: ViewBinding) {
        firstPair = null
        secondPair = null
        flippingPair = false

        pairList.shuffle()

        pairList.forEach { pair ->
            pair.flip = false
            pair.match = false
        }

        adapterPair.notifyDataSetChanged()
        stepSearchPair = 0
        updateTextStepPair(binding)
        gameStarted = false
        stopTimer()
    }

    private fun updateTextStepPair(binding: ViewBinding) {
        when (binding) {
            is FragmentFindPairGameBinding -> binding.textSteps.text = stepSearchPair.toString()
        }
    }

    private fun showWinMessage(context: Context) {
        Toast.makeText(context, R.string.toast_win, Toast.LENGTH_SHORT).show()
    }

    private fun saveBestStatsFindGame() {
        val levelStats = statsHighScoreFindPair[selectedLevelPairGame]!!

        if (elapsedTime < levelStats.bestTime) {
            levelStats.bestTime = elapsedTime
        }
        if (stepSearchPair < levelStats.bestSteps || levelStats.bestSteps == 0) {
            levelStats.bestSteps = stepSearchPair
        }

        HighScoreFindPairManager.saveStatsScoreFindPairGame(preferences)
        stepSearchPair = 0
    }

    private fun checkGameOver(): Boolean {
        return when (selectedLevelPairGame) {
            "Level 1", "Level 3" -> pairList.count { it.match } == pairList.size - 1
            else -> pairList.all { it.match }
        }
    }
}