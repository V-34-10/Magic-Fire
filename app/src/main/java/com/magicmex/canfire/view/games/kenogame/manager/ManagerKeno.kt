package com.magicmex.canfire.view.games.kenogame.manager

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.magicmex.canfire.R
import com.magicmex.canfire.adapter.kenogame.KenoGameAdapter
import com.magicmex.canfire.databinding.FragmentKenoGameBinding
import com.magicmex.canfire.model.kenogame.KenoGame
import com.magicmex.canfire.view.games.findgame.manager.GameSettings
import com.magicmex.canfire.view.games.kenogame.dialog.HighScoreKenoManager

object ManagerKeno {

    private lateinit var preferencesApp: SharedPreferences
    private lateinit var recyclerViewSceneGame: RecyclerView
    private lateinit var kenoGameAdapter: KenoGameAdapter
    private val selectedNumbers = mutableListOf<KenoGame>()
    private val winningNumbers = mutableListOf<KenoGame>()
    private var levelKenoGame: String = ""
    private var timer: CountDownTimer? = null
    private var elapsedTime: Long = 0
    private var gameStarted = false
    private var secondsUpdateTime = 1000L
    private var maxNumbers: String = ""

    @SuppressLint("NotifyDataSetChanged")
    fun initRecyclerKenoScene(binding: ViewBinding, context: Context) {
        preferencesApp = GameSettings.getPreference(context)
        val kenoNumbers = (1..35).map { KenoGame(it) }.toMutableList()
        kenoGameAdapter = KenoGameAdapter(
            kenoNumbers,
            selectedNumbers,
            winningNumbers,
            onItemClickListener = { selectedNumber ->
                handleNumberClick(selectedNumber, context, binding)
            }
        )

        recyclerViewSceneGame = when (binding) {
            is FragmentKenoGameBinding -> binding.sceneGame
            else -> throw IllegalArgumentException("Unsupported binding type")
        }

        recyclerViewSceneGame.adapter = kenoGameAdapter
        recyclerViewSceneGame.layoutManager = GridLayoutManager(context, 7)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleNumberClick(
        selectedNumber: KenoGame,
        context: Context,
        binding: ViewBinding
    ) {
        levelKenoGame = GameSettings.selectedLevel
        if (selectedNumbers.contains(selectedNumber)) {
            selectedNumbers.remove(selectedNumber)
            selectedNumber.isSelected = false
        } else {
            if (selectedNumbers.size < maxNumbers.getMaxNumbers()) {
                selectedNumbers.add(selectedNumber)
                selectedNumber.isSelected = true
            }
        }
        kenoGameAdapter.notifyDataSetChanged()
        updateSelectedNumbersText(context, binding)
    }

    @SuppressLint("SetTextI18n")
    private fun updateSelectedNumbersText(context: Context, binding: ViewBinding) {
        val selectedText = if (selectedNumbers.isEmpty()) {
            ""
        } else {
            selectedNumbers.joinToString(" ") { it.index.toString() }
        }
        when (binding) {
            is FragmentKenoGameBinding -> binding.textYouChoice.text =
                context.getString(R.string.text_default_choice_second) + selectedText
        }
    }

    fun startKenoGame(context: Context, binding: ViewBinding) {
        if (selectedNumbers.isEmpty()) return

        generateWinningNumbers()
        updateWinningNumbersText(binding)
        checkWinningCondition(context)
    }

    private fun generateWinningNumbers() {
        winningNumbers.clear()
        while (winningNumbers.size < maxNumbers.getMaxNumbers()) {
            val randomValue = (1..35).random()
            if (!winningNumbers.any { it.index == randomValue }) {
                winningNumbers.add(KenoGame(randomValue, isWinning = true))
            }
        }
    }

    private fun updateWinningNumbersText(binding: ViewBinding) {
        val winningText = winningNumbers.joinToString("  ") { it.index.toString() }
        when (binding) {
            is FragmentKenoGameBinding -> binding.textWinCombination.text = winningText
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun checkWinningCondition(context: Context) {
        val isWin = selectedNumbers.any { selected ->
            winningNumbers.any { it.index == selected.index }
        }
        val stats = HighScoreKenoManager.statsHighScoreKeno[levelKenoGame] ?: return

        stats.countAllGamesPlayed++

        if (isWin) {
            Toast.makeText(context, R.string.toast_win, Toast.LENGTH_SHORT).show()
            stats.countWins++
        } else {
            Toast.makeText(context, R.string.toast_lose, Toast.LENGTH_SHORT).show()
            stats.countLosses++
        }

        HighScoreKenoManager.saveStatsScoreKenoGame(preferencesApp)
        kenoGameAdapter.notifyDataSetChanged()
    }

    fun startTimer(binding: ViewBinding) {
        timer?.cancel()
        elapsedTime = 0
        gameStarted = true

        timer = object : CountDownTimer(Long.MAX_VALUE, secondsUpdateTime) {
            override fun onTick(millisUntilFinished: Long) {
                elapsedTime += secondsUpdateTime
                updateTimerUI(binding)
            }

            override fun onFinish() {}
        }.start()
    }

    private fun updateTimerUI(binding: ViewBinding) {
        when (binding) {
            is FragmentKenoGameBinding -> binding.textTime.text =
                String.format("%02d:%02d", (elapsedTime / 1000) / 60, (elapsedTime / 1000) % 60)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun restartGame(context: Context, binding: ViewBinding) {
        timer?.cancel()
        selectedNumbers.clear()
        winningNumbers.clear()
        kenoGameAdapter.notifyDataSetChanged()
        updateSelectedNumbersText(context, binding)

        when (binding) {
            is FragmentKenoGameBinding -> {
                binding.textWinCombination.text = ""
                binding.textTime.text = context.getString(R.string.text_default_time)
            }
        }

        startTimer(binding)
    }
}