package com.magicmex.canfire.view.games.kenogame

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.magicmex.canfire.R
import com.magicmex.canfire.adapter.kenogame.KenoGameAdapter
import com.magicmex.canfire.databinding.FragmentKenoGameBinding
import com.magicmex.canfire.model.kenogame.KenoGame
import com.magicmex.canfire.view.games.kenogame.dialog.DialogFragmentsHighScoreKeno
import com.magicmex.canfire.view.games.kenogame.dialog.HighScoreKenoManager
import com.magicmex.canfire.view.level.LevelsActivity
import com.magicmex.canfire.view.settings.MusicController
import com.magicmex.canfire.view.settings.MusicStart

class KenoGameFragment : Fragment() {
    private lateinit var binding: FragmentKenoGameBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var controllerMusic: MusicController
    private lateinit var kenoGameAdapter: KenoGameAdapter
    private val selectedNumbers = mutableListOf<KenoGame>()
    private val winningNumbers = mutableListOf<KenoGame>()
    private var levelKenoGame: String = ""
    private var timer: CountDownTimer? = null
    private var elapsedTime: Long = 0
    private var gameStarted = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKenoGameBinding.inflate(layoutInflater, container, false)

        controllerMusic = context?.let { MusicController(it) }!!
        preferences =
            requireActivity().getSharedPreferences(
                "MagicMexicanFirePref",
                AppCompatActivity.MODE_PRIVATE
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MusicStart.musicStartMode(R.raw.music_keno, controllerMusic, preferences)

        levelKenoGame = preferences.getString("LevelGame", "").toString()
        when (levelKenoGame) {
            "Level 1" -> binding.statusLevel.setBackgroundResource(R.drawable.background_status_level_first_keno)
            "Level 2" -> binding.statusLevel.setBackgroundResource(R.drawable.background_status_level_second_keno)
            "Level 3" -> binding.statusLevel.setBackgroundResource(R.drawable.background_status_level_three_keno)
        }

        initRecyclerScene()
        initControlBarKenoGame()
        startTimer()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerScene() {
        val kenoNumbers = (1..35).map { KenoGame(it) }.toMutableList()
        kenoGameAdapter = KenoGameAdapter(
            kenoNumbers,
            selectedNumbers,
            winningNumbers,
            onItemClickListener = { selectedNumber ->
                handleNumberClick(selectedNumber)
            }
        )

        binding.sceneGame.adapter = kenoGameAdapter
        binding.sceneGame.layoutManager = GridLayoutManager(context, 7)

        binding.btnClaim.setOnClickListener {
            startKenoGame()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleNumberClick(selectedNumber: KenoGame) {
        if (selectedNumbers.contains(selectedNumber)) {
            selectedNumbers.remove(selectedNumber)
            selectedNumber.isSelected = false
        } else {
            val maxNumbers = when (levelKenoGame) {
                "Level 1" -> 6
                "Level 2" -> 4
                "Level 3" -> 2
                else -> 2
            }
            if (selectedNumbers.size < maxNumbers) {
                selectedNumbers.add(selectedNumber)
                selectedNumber.isSelected = true
            }
        }
        kenoGameAdapter.notifyDataSetChanged()
        updateSelectedNumbersText()
    }

    @SuppressLint("SetTextI18n")
    private fun updateSelectedNumbersText() {
        val selectedText = if (selectedNumbers.isEmpty()) {
            ""
        } else {
            selectedNumbers.joinToString(" ") { it.index.toString() }
        }
        binding.textYouChoice.text = getString(R.string.text_default_choice_second) + selectedText
    }

    private fun startKenoGame() {
        if (selectedNumbers.isEmpty()) return

        generateWinningNumbers()
        updateWinningNumbersText()
        checkWinningCondition()
    }

    private fun generateWinningNumbers() {
        winningNumbers.clear()
        val maxNumbers = when (levelKenoGame) {
            "Level 1" -> 6
            "Level 2" -> 4
            "Level 3" -> 2
            else -> 2
        }
        while (winningNumbers.size < maxNumbers) {
            val randomValue = (1..35).random()
            if (!winningNumbers.any { it.index == randomValue }) {
                winningNumbers.add(KenoGame(randomValue, isWinning = true))
            }
        }
    }

    private fun updateWinningNumbersText() {
        val winningText = winningNumbers.joinToString("  ") { it.index.toString() }
        binding.textWinCombination.text = winningText
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun checkWinningCondition() {
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

        HighScoreKenoManager.saveStatsScoreKenoGame(preferences)
        kenoGameAdapter.notifyDataSetChanged()
    }

    private fun initControlBarKenoGame() {
        var animationButton = AnimationUtils.loadAnimation(context, R.anim.scale_animation)
        binding.btnClaim.setOnClickListener {
            it.startAnimation(animationButton)
            startKenoGame()
        }
        binding.btnRestart.setOnClickListener {
            animationButton = AnimationUtils.loadAnimation(context, R.anim.scale_animation)
            it.startAnimation(animationButton)
            restartGame()
        }
        binding.btnHighScore.setOnClickListener {
            animationButton = AnimationUtils.loadAnimation(context, R.anim.scale_animation)
            it.startAnimation(animationButton)
            DialogFragmentsHighScoreKeno.runDialogKenoGame(requireContext(), preferences)
        }
        binding.btnChange.setOnClickListener {
            animationButton = AnimationUtils.loadAnimation(context, R.anim.scale_animation)
            it.startAnimation(animationButton)
            startActivity(Intent(context, LevelsActivity::class.java))
            activity?.finish()
        }
    }

    private fun startTimer() {
        timer?.cancel()
        elapsedTime = 0
        gameStarted = true

        timer = object : CountDownTimer(Long.MAX_VALUE, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                elapsedTime += 1000L
                updateTimerUI()
            }

            override fun onFinish() {}
        }.start()
    }

    private fun updateTimerUI() {
        binding.textTime.text =
            String.format("%02d:%02d", (elapsedTime / 1000) / 60, (elapsedTime / 1000) % 60)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun restartGame() {
        timer?.cancel()
        selectedNumbers.clear()
        winningNumbers.clear()
        kenoGameAdapter.notifyDataSetChanged()
        updateSelectedNumbersText()
        binding.textWinCombination.text = ""
        binding.textTime.text = getString(R.string.text_default_time)
        startTimer()
    }

    override fun onResume() {
        super.onResume()
        controllerMusic.resume()
    }

    override fun onPause() {
        super.onPause()
        controllerMusic.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        controllerMusic.release()
    }
}