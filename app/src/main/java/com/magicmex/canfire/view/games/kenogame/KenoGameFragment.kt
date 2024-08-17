package com.magicmex.canfire.view.games.kenogame

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.magicmex.canfire.R
import com.magicmex.canfire.adapter.KenoGameAdapter
import com.magicmex.canfire.databinding.FragmentKenoGameBinding
import com.magicmex.canfire.model.KenoGame
import com.magicmex.canfire.view.games.findgame.manager.GameSettings
import com.magicmex.canfire.view.games.kenogame.dialog.DialogFragmentsHighScoreKeno
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
                "Level 1" -> 2
                "Level 2" -> 4
                "Level 3" -> 6
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

    private fun updateSelectedNumbersText() {
        val selectedText = if (selectedNumbers.isEmpty()) {
            getString(R.string.text_default_choice)
        } else {
            selectedNumbers.joinToString(" ") { it.index.toString() }
        }
        binding.textYouChoice.text = selectedText
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
            "Level 1" -> 2
            "Level 2" -> 4
            "Level 3" -> 6
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
        val isWin = selectedNumbers.any { winningNumbers.contains(it) }
        if (isWin) {
            Toast.makeText(context, R.string.toast_win, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, R.string.toast_lose, Toast.LENGTH_SHORT).show()
        }
        kenoGameAdapter.notifyDataSetChanged()
    }

    private fun initControlBarKenoGame() {
        var animationButton = AnimationUtils.loadAnimation(context, R.anim.scale_animation)
        binding.btnClaim.setOnClickListener {
            it.startAnimation(animationButton)

        }
        binding.btnHighScore.setOnClickListener {
            animationButton = AnimationUtils.loadAnimation(context, R.anim.scale_animation)
            it.startAnimation(animationButton)
            DialogFragmentsHighScoreKeno.runDialogKenoGame(requireContext())
        }
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