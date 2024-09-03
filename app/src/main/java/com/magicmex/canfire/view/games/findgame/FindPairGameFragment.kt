package com.magicmex.canfire.view.games.findgame

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.magicmex.canfire.R
import com.magicmex.canfire.databinding.FragmentFindPairGameBinding
import com.magicmex.canfire.utils.animation.AnimationManager.setAnimationClickButton
import com.magicmex.canfire.utils.preference.PreferenceManager
import com.magicmex.canfire.utils.preference.PreferenceManager.initSettings
import com.magicmex.canfire.view.games.findgame.dialog.DialogFragmentsHighScoreFindPair
import com.magicmex.canfire.view.games.findgame.manager.FindPairGameManager
import com.magicmex.canfire.view.level.LevelsActivity
import com.magicmex.canfire.view.settings.music.MusicController
import com.magicmex.canfire.view.settings.music.MusicStart

class FindPairGameFragment : Fragment() {
    private lateinit var binding: FragmentFindPairGameBinding
    private lateinit var controllerMusic: MusicController
    private lateinit var findPairGameManager: FindPairGameManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFindPairGameBinding.inflate(layoutInflater, container, false)
        controllerMusic = context?.let { MusicController(it) }!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSettings(requireContext())
        MusicStart.musicStartMode(R.raw.music_find_pair, controllerMusic)
        PreferenceManager.initLevelGame(requireContext())
        setUIBackgroundDependedLevel()
        context?.let { findPairGameManager = FindPairGameManager(it, binding) }
        findPairGameManager.initGame(PreferenceManager.selectedLevel)
        initControlBarGame()
    }

    private fun setUIBackgroundDependedLevel() {
        when (PreferenceManager.selectedLevel) {
            "Level 1" -> binding.statusLevel.setBackgroundResource(R.drawable.background_status_level_first)
            "Level 2" -> {
                binding.statusLevel.setBackgroundResource(R.drawable.background_status_level_second)
                binding.backgroundGameFindPair.setBackgroundResource(R.drawable.background_find_pair_game_level_second)
            }

            "Level 3" -> {
                binding.statusLevel.setBackgroundResource(R.drawable.background_status_level_three)
                binding.backgroundGameFindPair.setBackgroundResource(R.drawable.background_find_pair_game_level_three)
            }
        }
    }

    private fun initControlBarGame() {
        binding.btnClaim.setOnClickListener {
            it.startAnimation(setAnimationClickButton(requireContext()))
            findPairGameManager.resetGame()
        }
        binding.btnHighScore.setOnClickListener {
            it.startAnimation(setAnimationClickButton(requireContext()))
            findPairGameManager.resetGame()
            DialogFragmentsHighScoreFindPair.runDialogFindPairGame(
                requireContext(),
                PreferenceManager.getPreference(requireContext())
            )
        }
        binding.btnChange.setOnClickListener {
            it.startAnimation(setAnimationClickButton(requireContext()))
            startActivity(Intent(context, LevelsActivity::class.java))
            activity?.finish()
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